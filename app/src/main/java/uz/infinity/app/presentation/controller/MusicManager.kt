package uz.infinity.app.presentation.controller

import android.database.Cursor
import androidx.lifecycle.MutableLiveData
import uz.infinity.app.data.model.CommandEnum
import uz.infinity.app.data.model.MusicData

object MusicManager {
    var selectMusicPos: Int = -1
    var cursor: Cursor? = null
    var lastCommand : CommandEnum = CommandEnum.PLAY
    var currentTime : Long = 0L
    var fullTime : Long = 0L
    val currentTimeLiveData = MutableLiveData<Long>()

    val playMusicLiveData = MutableLiveData<MusicData>()
    val isPlayingLiveData = MutableLiveData<Boolean>()

}