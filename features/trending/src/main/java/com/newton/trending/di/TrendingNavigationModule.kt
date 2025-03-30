package com.newton.trending.di

import com.newton.trending.navigation.TrendingNavigationApi
import com.newton.trending.navigation.TrendingNavigationApiImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TrendingNavigationModule {

    @Provides
    @Singleton
    fun provideTrendingNavigationApi()
    :TrendingNavigationApi {
        return TrendingNavigationApiImpl()
    }
}