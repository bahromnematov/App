package uz.infinity.app.presentation.ui.service
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import uz.infinity.app.R
import uz.infinity.app.data.mapper.getMusicDataByPosition
import uz.infinity.app.data.model.CommandEnum
import uz.infinity.app.data.model.MusicData
import uz.infinity.app.presentation.controller.MusicManager
import uz.infinity.app.utils.myLog

@AndroidEntryPoint
class MusicService : Service() {
    override fun onBind(intent: Intent?): IBinder? = null
    private val CHANNEl_ID = "GITA"
    private var manager: NotificationManager? = null
    private var _mediaPlayer: MediaPlayer? = null
    private val mediaPlayer get() = _mediaPlayer!!
    private val scope = CoroutineScope(Dispatchers.IO + Job())
    private var job: Job? = null

//    @Inject
//    lateinit var controller: MusicController

    override fun onCreate() {
        super.onCreate()
        _mediaPlayer = MediaPlayer()
        createChannel()
        createNotification()
    }

    private fun createChannel() {
        if (Build.VERSION.SDK_INT >= 26) {
            val channel =
                NotificationChannel(CHANNEl_ID, "Example", NotificationManager.IMPORTANCE_DEFAULT)
            channel.setSound(null, null)
            manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager?.createNotificationChannel(channel)
        }
    }

    private fun createNotification() {
        val notification = NotificationCompat.Builder(this, CHANNEl_ID)
            .setSmallIcon(R.drawable.ic_music)
            .setContentTitle("GITA")
            .setCustomContentView(createRemoteView())
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .build()

        startForeground(1, notification)
    }

    private fun createRemoteView(): RemoteViews {
        val remoteView = RemoteViews(this.packageName, R.layout.remote_view)
        val musicData = MusicManager.cursor?.getMusicDataByPosition(MusicManager.selectMusicPos)
        remoteView.setTextViewText(R.id.textMusicName, musicData?.title)
        remoteView.setTextViewText(R.id.textArtistName, musicData?.artist)
        if (mediaPlayer.isPlaying) {
            remoteView.setImageViewResource(R.id.buttonManage, R.drawable.ic_pause)
        } else remoteView.setImageViewResource(R.id.buttonManage, R.drawable.ic_play)

        remoteView.setOnClickPendingIntent(R.id.buttonPrev, createPendingIntent(CommandEnum.PREV))
        remoteView.setOnClickPendingIntent(
            R.id.buttonCancel,
            createPendingIntent(CommandEnum.CANCEL)
        )
        remoteView.setOnClickPendingIntent(R.id.buttonNext, createPendingIntent(CommandEnum.NEXT))
        remoteView.setOnClickPendingIntent(
            R.id.buttonManage,
            createPendingIntent(CommandEnum.MANAGE)
        )
        return remoteView
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun createPendingIntent(commandEnum: CommandEnum): PendingIntent {
        val intent = Intent(this, MusicService::class.java)
        MusicManager.lastCommand = commandEnum
        return PendingIntent.getService(
            this, commandEnum.pos, intent, PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotification()
       doneCommand(MusicManager.lastCommand, this)
        myLog("command = ${MusicManager.lastCommand}")
        return START_NOT_STICKY
    }


  private  fun doneCommand(commandEnum: CommandEnum,context: Context) {
//        val data: MusicData = MusicManager.cursor?.getMusicDataByPosition(MusicManager.selectMusicPos)!!
        when (commandEnum) {
            CommandEnum.MANAGE -> {
                if (_mediaPlayer == null) {
                    doneCommand(CommandEnum.PLAY,context)
                    return
                }
                if (mediaPlayer.isPlaying) {
                    doneCommand(CommandEnum.PAUSE,context )
                } else doneCommand(CommandEnum.PLAY,context )
            }
            CommandEnum.PLAY -> {
//                _mediaPlayer = MediaPlayer.create(
//                    context,
//                    Uri.parse(cursor.getMusicDataByPosition(MusicManager.selectMusicPos).data)
//                )
                if (mediaPlayer.isPlaying) {
                    mediaPlayer.stop()
                    _mediaPlayer = MediaPlayer.create(
                        this,
                        Uri.parse(MusicManager.cursor!!.getMusicDataByPosition(MusicManager.selectMusicPos).data)
                    )
//                    mediaPlayer.start()
                }
                mediaPlayer.setOnCompletionListener {
                    MusicManager.selectMusicPos++
//                    doneCommand(CommandEnum.NEXT,context)
                }
                mediaPlayer.start()
//                MusicManager.fullTime = data.duration
                mediaPlayer.seekTo(MusicManager.currentTime.toInt())
                job?.let { it.cancel() }
                job = scope.launch {
                    changeProgress().collectLatest {
                        MusicManager.currentTime = it
                        MusicManager.currentTimeLiveData.postValue(it)
                    }
                }
                MusicManager.isPlayingLiveData.value = true
//                MusicManager.playMusicLiveData.value = data
                mediaPlayer.start()
                createNotification()
            }


            CommandEnum.PAUSE -> {
                mediaPlayer.stop()
                job?.cancel()
                MusicManager.isPlayingLiveData.value = false
                createNotification()

            }
            CommandEnum.PREV -> {
                MusicManager.currentTime = 0
                if (MusicManager.selectMusicPos == 0)
                    MusicManager.selectMusicPos = MusicManager.cursor!!.count - 1
                else MusicManager.selectMusicPos--
                doneCommand(CommandEnum.PLAY,context)

            }
            CommandEnum.NEXT -> {
                MusicManager.currentTime = 0
//                if (MusicManager.selectMusicPos + 1 == MusicManager.cursor!!.count)
                    MusicManager.selectMusicPos = 0
//                else MusicManager.selectMusicPos++
                doneCommand(CommandEnum.PLAY,context)

            }
            CommandEnum.CANCEL -> {
                mediaPlayer.stop()
                stopSelf()


            }
        }
    }

    private fun changeProgress(): Flow<Long> = flow {
        for (i in MusicManager.currentTime until MusicManager.fullTime step 1000) {
            delay(1000)
            emit(i)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        job?.cancel()
    }
}

