package com.newton.search.di

import com.newton.search.navigation.SearchNavigationApi
import com.newton.search.navigation.SearchNavigationApiImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SearchNavigationModule {

    @Provides
    @Singleton
    fun provideSearchNavigationApi(): SearchNavigationApi = SearchNavigationApiImpl()
}