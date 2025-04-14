package com.newton.shared.di

import com.newton.network.data.remote.*
import com.newton.network.domain.repositories.*
import com.newton.shared.sharedRepository.*
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
        moviesApiService: MoviesApiService
    ): FilmDetailsRepository = FilmDetailsRepositoryImpl(moviesApiService)
}