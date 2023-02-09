package uz.infinity.app.domain.repository

import android.database.Cursor

interface AppRepository {
    suspend fun getMusicsFromLocal()
    fun getCursor() : Cursor
}