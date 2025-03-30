package com.newton.network.data.di

import com.newton.network.data.remote.TrendingApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiServiceModule {

    @Provides
    @Singleton
    fun provideTrendingApiService(retrofit: Retrofit): TrendingApiService {
        return retrofit.create(TrendingApiService::class.java)
    }
}