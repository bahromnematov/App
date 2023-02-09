package uz.infinity.app.presentation.viewmodel.impl

import android.database.Cursor
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.infinity.app.domain.usecase.CursorRefUseCase
import uz.infinity.app.presentation.viewmodel.PlayListViewModel
import javax.inject.Inject

@HiltViewModel
class PlayListViewModelImpl @Inject constructor(
    private val cursorRefUseCase: CursorRefUseCase
) : ViewModel(), PlayListViewModel {
    override val cursorRefFlow = MutableStateFlow<Cursor?>(null)

    init {
        cursorRefUseCase.invoke().onEach {
            cursorRefFlow.emit(it)
        }.launchIn(viewModelScope)
    }
}