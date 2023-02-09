package uz.infinity.app.presentation.controller.impl
//
//import android.content.Context
//import android.database.Cursor
//import android.media.MediaPlayer
//import android.net.Uri
//import kotlinx.coroutines.*
//import kotlinx.coroutines.flow.*
//import uz.infinity.app.data.mapper.getMusicDataByPosition
//import uz.infinity.app.data.model.CommandEnum
//import uz.infinity.app.data.model.MusicData
//import uz.infinity.app.domain.usecase.CursorRefUseCase
//import uz.infinity.app.presentation.controller.MusicController
//import uz.infinity.app.presentation.controller.MusicManager
//import javax.inject.Inject
//
//class MusicControllerImpl @Inject constructor(
//    cursorRefUseCase: CursorRefUseCase
//) : MusicController {
//    private var _cursor: Cursor? = null
//    private val cursor get() = _cursor!!
//    private val scope = CoroutineScope(Dispatchers.IO + Job())
//    private var job: Job? = null
//    private var _mediaPlayer: MediaPlayer? = null
//    private val mediaPlayer get() = _mediaPlayer!!
//
//    override fun doneCommand(commandEnum: CommandEnum, context: Context) {
//        val data: MusicData =
//            MusicManager.cursor?.getMusicDataByPosition(MusicManager.selectMusicPos)!!
//        when (commandEnum) {
//            CommandEnum.MANAGE -> {
//                if (_mediaPlayer == null) {
//                    doneCommand(CommandEnum.PLAY, context)
//                    return
//                }
//                if (mediaPlayer.isPlaying) {
//                    doneCommand(CommandEnum.PAUSE, context)
//                } else doneCommand(CommandEnum.PLAY, context)
//            }
//            CommandEnum.PLAY -> {
////                _mediaPlayer = MediaPlayer.create(
////                    context,
////                    Uri.parse(cursor.getMusicDataByPosition(MusicManager.selectMusicPos).data)
////                )
//                if (mediaPlayer.isPlaying) {
//                    mediaPlayer.stop()
//                    _mediaPlayer = MediaPlayer.create(
//                        context,
//                        Uri.parse(cursor.getMusicDataByPosition(MusicManager.selectMusicPos).data)
//                    )
//                    mediaPlayer.start()
//                }
//                mediaPlayer.setOnCompletionListener {
//                    MusicManager.selectMusicPos++
//                    doneCommand(CommandEnum.NEXT, context)
//                }
//                MusicManager.fullTime = data.duration
//                mediaPlayer.seekTo(MusicManager.currentTime.toInt())
//                job?.let { it.cancel() }
//                job = scope.launch {
//                    changeProgress().collectLatest {
//                        MusicManager.currentTime = it
//                        MusicManager.currentTimeLiveData.postValue(it)
//                    }
//                }
//                MusicManager.isPlayingLiveData.value = true
//                MusicManager.playMusicLiveData.value = data
////                mediaPlayer.start()
//            }
//
//
//            CommandEnum.PAUSE -> {
//                mediaPlayer.stop()
//                job?.let { it.cancel() }
//                MusicManager.isPlayingLiveData.value = false
//
//            }
//            CommandEnum.PREV -> {
//                MusicManager.currentTime = 0
//                if (MusicManager.selectMusicPos == 0)
//                    MusicManager.selectMusicPos = MusicManager.cursor!!.count - 1
//                else MusicManager.selectMusicPos--
//                doneCommand(CommandEnum.PLAY, context)
//
//            }
//            CommandEnum.NEXT -> {
//                MusicManager.currentTime = 0
//                if (MusicManager.selectMusicPos + 1 == MusicManager.cursor!!.count)
//                    MusicManager.selectMusicPos = 0
//                else MusicManager.selectMusicPos++
//                doneCommand(CommandEnum.PLAY, context)
//
//            }
//            CommandEnum.CANCEL -> {
//                mediaPlayer.stop()
//
//
//            }
//        }
//    }
//
//    private fun changeProgress(): Flow<Long> = flow {
//        for (i in MusicManager.currentTime until MusicManager.fullTime step 1000) {
//            delay(1000)
//            emit(i)
//        }
//    }
//
//    init {
//        cursorRefUseCase.invoke().onEach { _cursor = it }.launchIn(scope)
//    }
//
//    override fun onCleared() {
//        scope.cancel()
//        _cursor = null
//        _mediaPlayer?.release()
//        _mediaPlayer = null
//
//    }
//}