package com.newton.movies.di

import com.newton.movies.navigation.*
import dagger.*
import dagger.hilt.*
import dagger.hilt.components.*
import javax.inject.*

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