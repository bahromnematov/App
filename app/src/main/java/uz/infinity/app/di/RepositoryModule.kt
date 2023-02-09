package uz.infinity.app.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.infinity.app.domain.repository.AppRepository
import uz.infinity.app.domain.repository.impl.AppRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindsAppRepository(impl: AppRepositoryImpl): AppRepository

}