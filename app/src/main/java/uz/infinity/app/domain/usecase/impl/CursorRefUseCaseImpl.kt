package uz.infinity.app.domain.usecase.impl

import kotlinx.coroutines.flow.flow
import uz.infinity.app.domain.repository.AppRepository
import uz.infinity.app.domain.usecase.CursorRefUseCase
import javax.inject.Inject

class CursorRefUseCaseImpl @Inject constructor(
    private val appRepository: AppRepository
) : CursorRefUseCase {
    override fun invoke() = flow { emit(appRepository.getCursor()) }
}