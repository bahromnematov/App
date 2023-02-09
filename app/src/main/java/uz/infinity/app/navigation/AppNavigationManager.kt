package uz.infinity.app.navigation

import androidx.navigation.NavController
import androidx.navigation.NavDirections
import kotlinx.coroutines.flow.MutableSharedFlow
import uz.infinity.memorygamebottcamp4.navigation.AppNavigator
import uz.infinity.memorygamebottcamp4.navigation.NavigationHandler
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppNavigationManager @Inject constructor() : AppNavigator, NavigationHandler {
    override val navigationFlow = MutableSharedFlow<NavController.() -> Unit>()

    private suspend fun navigate(block: NavController.() -> Unit) {
        navigationFlow.emit(block)
    }

    override suspend fun back() = navigate { popBackStack() }
    override suspend fun navigationTo(direction: NavDirections) = navigate { navigate(direction) }
}

