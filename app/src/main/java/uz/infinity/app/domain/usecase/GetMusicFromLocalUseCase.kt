package uz.infinity.app.domain.usecase

import kotlinx.coroutines.flow.Flow

interface GetMusicFromLocalUseCase {
    fun invoke(): Flow<Unit>
}