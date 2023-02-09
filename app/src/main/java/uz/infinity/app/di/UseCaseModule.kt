package uz.infinity.app.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import uz.infinity.app.domain.usecase.CursorRefUseCase
import uz.infinity.app.domain.usecase.GetMusicFromLocalUseCase
import uz.infinity.app.domain.usecase.impl.CursorRefUseCaseImpl
import uz.infinity.app.domain.usecase.impl.GetMusicFromLocalUseCaseImpl

@Module
@InstallIn(SingletonComponent::class)
interface UseCaseModule {

    @Binds
    fun bindsCursorRefUseCase(impl: CursorRefUseCaseImpl): CursorRefUseCase

    @Binds
    fun bindsGetMusicFromLocalUseCase(impl: GetMusicFromLocalUseCaseImpl): GetMusicFromLocalUseCase
}