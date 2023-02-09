package uz.infinity.app.data.mapper

import android.database.Cursor
import uz.infinity.app.data.model.MusicData

fun Cursor.getMusicDataByPosition(pos: Int): MusicData {
    this.moveToPosition(pos)
    return MusicData(
        this.getLong(0),
        this.getString(1),
        this.getString(2),
        this.getString(3),
        this.getLong(4)
    )
}