package com.newton.shows.di

import com.newton.network.data.remote.FilmApiService
import com.newton.domain.repository.ShowsRepository
import com.newton.shows.data.repository.ShowsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ShowRepositoryModule {

    @Provides
    @Singleton
    fun provideShowRepositoryModule(
        filmApiService: FilmApiService
    ): ShowsRepository = ShowsRepositoryImpl(filmApiService)
}