package uz.infinity.app.presentation.viewmodel.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.infinity.app.domain.usecase.GetMusicFromLocalUseCase
import uz.infinity.app.presentation.ui.screen.SplashScreenDirections
import uz.infinity.app.presentation.viewmodel.SplashViewModel
import uz.infinity.memorygamebottcamp4.navigation.NavigationHandler
import javax.inject.Inject

@HiltViewModel
class SplashViewModelImpl @Inject constructor(
    private val navigationHandler: NavigationHandler,
    private val getMusicFromLocalUseCase: GetMusicFromLocalUseCase
) : ViewModel(), SplashViewModel {

    override fun getMusicFromLocal() {
        getMusicFromLocalUseCase.invoke().onEach {
            delay(1000)
            navigationHandler.navigationTo(SplashScreenDirections.actionSplashScreenToPlayListScreen())
        }.launchIn(viewModelScope)
    }
}