package uz.infinity.app.domain.repository.impl

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import dagger.hilt.android.qualifiers.ApplicationContext
import uz.infinity.app.domain.repository.AppRepository
import uz.infinity.app.utils.myLog
import java.io.InputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : AppRepository {
    private lateinit var myCursor: Cursor
    private val projection = arrayOf(
        MediaStore.Audio.Media._ID,
        MediaStore.Audio.Media.ARTIST,
        MediaStore.Audio.Media.TITLE,
        MediaStore.Audio.Media.DATA,
        MediaStore.Audio.Media.DURATION
    )

    @SuppressLint("Recycle")
    override suspend fun getMusicsFromLocal() {
        val cursor = context.contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            projection,
            MediaStore.Audio.Media.IS_MUSIC + "!=0",
            null,
            null
        )
        myLog("count  = ${cursor?.count}")
        cursor?.let { myCursor = it }
    }

    override fun getCursor() = myCursor
}

