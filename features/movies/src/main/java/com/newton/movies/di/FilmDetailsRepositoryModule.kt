package com.newton.movies.di

import com.newton.movies.data.repository.MoviesRepositoryImpl
import com.newton.network.data.remote.FilmApiService
import com.newton.domain.repository.MoviesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FilmDetailsRepositoryModule {

    @Provides
    @Singleton
    fun provideFilmDetailsRepository(
        moviesApiService: FilmApiService
    ): MoviesRepository = MoviesRepositoryImpl(moviesApiService)
}