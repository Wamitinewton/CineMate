package com.newton.auth.di

import com.newton.auth.data.TmdbAuthRepositoryImpl
import com.newton.auth.data.local.TmdbAuthLocalDataSource
import com.newton.domain.repository.TmdbAuthRepository
import com.newton.network.data.remote.TmdbAuthApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthRepositoryModule {

    @Provides
    @Singleton
    fun provideTmdbAuthRepository(
        apiService: TmdbAuthApiService,
        localDataSource: TmdbAuthLocalDataSource
    ): TmdbAuthRepository = TmdbAuthRepositoryImpl(apiService, localDataSource)

}