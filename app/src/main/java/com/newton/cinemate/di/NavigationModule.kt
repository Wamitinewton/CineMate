package com.newton.cinemate.di

import com.newton.auth.navigation.AuthNavigationApi
import com.newton.cinemate.navigation.NavigationSubGraphs
import com.newton.movies.navigation.MoviesNavigationApi
import com.newton.trending.navigation.TrendingNavigationApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object NavigationModule {

    @Provides
    fun provideNavigationSubGraphs(
        authNavigationApi: AuthNavigationApi,
        trendingNavigationApi: TrendingNavigationApi,
        moviesNavigationApi: MoviesNavigationApi
    ): NavigationSubGraphs {
        return NavigationSubGraphs(
            authNavigationApi,
            trendingNavigationApi,
            moviesNavigationApi
        )
    }
}