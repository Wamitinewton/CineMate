package com.newton.movies.di

import com.newton.movies.navigation.MoviesNavigationApi
import com.newton.movies.navigation.MoviesNavigationApiImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MoviesNavigationModule {

    @Provides
    @Singleton
    fun provideMoviesNavigationApi(
    ): MoviesNavigationApi {
        return MoviesNavigationApiImpl()
    }
}