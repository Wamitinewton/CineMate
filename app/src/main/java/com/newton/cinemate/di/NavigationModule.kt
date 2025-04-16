package com.newton.cinemate.di

import com.newton.auth.navigation.*
import com.newton.cinemate.navigation.*
import com.newton.movies.navigation.*
import com.newton.shows.navigation.ShowsNavigationApi
import com.newton.trending.navigation.*
import dagger.*
import dagger.hilt.*
import dagger.hilt.components.*

@Module
@InstallIn(SingletonComponent::class)
object NavigationModule {

    @Provides
    fun provideNavigationSubGraphs(
        authNavigationApi: AuthNavigationApi,
        trendingNavigationApi: TrendingNavigationApi,
        moviesNavigationApi: MoviesNavigationApi,
        showsNavigationApi: ShowsNavigationApi
    ): NavigationSubGraphs {
        return NavigationSubGraphs(
            authNavigationApi,
            trendingNavigationApi,
            moviesNavigationApi,
            showsNavigationApi = showsNavigationApi
        )
    }
}