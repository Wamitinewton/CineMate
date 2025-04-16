package com.newton.trending.di

import com.newton.domain.repository.TrendingRepository
import com.newton.network.data.remote.*
import com.newton.trending.data.*
import dagger.*
import dagger.hilt.*
import dagger.hilt.components.*

@Module
@InstallIn(SingletonComponent::class)
object TrendingRepositoryModule {

    @Provides
    fun provideTrendingRepository(
        filmApiService: FilmApiService
    ): TrendingRepository {
        return TrendingRepositoryImpl(filmApiService)
    }
}