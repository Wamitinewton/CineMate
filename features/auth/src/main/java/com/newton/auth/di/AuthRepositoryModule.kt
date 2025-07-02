package com.newton.auth.di

import com.google.firebase.auth.*
import com.google.firebase.firestore.*
import com.newton.auth.data.*
import com.newton.auth.data.local.TmdbAuthLocalDataSource
import com.newton.auth.presentation.manager.*
import com.newton.domain.repository.AuthRepository
import com.newton.domain.repository.TmdbAuthRepository
import com.newton.network.data.remote.TmdbAuthApiService
import dagger.*
import dagger.hilt.*
import dagger.hilt.components.*
import javax.inject.*

@Module
@InstallIn(SingletonComponent::class)
object AuthRepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(
        firebaseAuth: FirebaseAuth,
        firestore: FirebaseFirestore
    ): AuthRepository {
        return AuthRepositoryImpl(firebaseAuth, firestore)
    }

    @Provides
    @Singleton
    fun provideTmdbAuthRepository(
        apiService: TmdbAuthApiService,
        localDataSource: TmdbAuthLocalDataSource
    ): TmdbAuthRepository = TmdbAuthRepositoryImpl(apiService, localDataSource)

}