package com.newton.shows.di

import com.newton.shows.navigation.ShowsNavigationApi
import com.newton.shows.navigation.ShowsNavigationApiImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ShowsNavigationModule {

    @Provides
    @Singleton
    fun provideShowsNavigationApi(): ShowsNavigationApi = ShowsNavigationApiImpl()
}