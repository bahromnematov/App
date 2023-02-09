package uz.infinity.app.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import uz.infinity.app.domain.repository.AppRepository
import uz.infinity.app.domain.usecase.GetMusicFromLocalUseCase
import javax.inject.Inject

class GetMusicFromLocalUseCaseImpl @Inject constructor(
    private val appRepository: AppRepository
) : GetMusicFromLocalUseCase {

    override fun invoke(): Flow<Unit> = flow {
        emit(appRepository.getMusicsFromLocal())
    }
}