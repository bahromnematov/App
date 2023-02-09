package uz.infinity.app.domain.usecase

import android.database.Cursor
import kotlinx.coroutines.flow.Flow

interface CursorRefUseCase {
    fun invoke() : Flow<Cursor>
}