package uz.infinity.app.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.infinity.app.navigation.AppNavigationManager
import uz.infinity.memorygamebottcamp4.navigation.AppNavigator
import uz.infinity.memorygamebottcamp4.navigation.NavigationHandler

@Module
@InstallIn(SingletonComponent::class)
interface NavigationModule {

    @Binds
    fun bindsAppNavigator(impl: AppNavigationManager): AppNavigator

    @Binds
    fun bindsNavigationHandler(impl: AppNavigationManager): NavigationHandler
}

