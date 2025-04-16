package com.newton.trending.di

import com.newton.trending.navigation.*
import dagger.*
import dagger.hilt.*
import dagger.hilt.components.*
import javax.inject.*

@Module
@InstallIn(SingletonComponent::class)
object TrendingNavigationModule {

    @Provides
    @Singleton
    fun provideTrendingNavigationApi()
            : TrendingNavigationApi {
        return TrendingNavigationApiImpl()
    }
}