package com.newton.trending.di

import com.newton.network.data.remote.TrendingApiService
import com.newton.network.domain.repositories.TrendingRepository
import com.newton.trending.data.TrendingRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object TrendingRepositoryModule {

    @Provides
    fun provideTrendingRepository(
        trendingApiService: TrendingApiService
    ): TrendingRepository {
        return TrendingRepositoryImpl(trendingApiService)
    }
}