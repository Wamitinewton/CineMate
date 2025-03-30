package com.newton.auth.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.newton.auth.data.AuthRepositoryImpl
import com.newton.auth.presentation.manager.GoogleClientManager
import com.newton.network.domain.repositories.AuthRepository
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
    fun provideAuthRepository(
        firebaseAuth: FirebaseAuth,
        firestore: FirebaseFirestore
    ): AuthRepository {
        return AuthRepositoryImpl(firebaseAuth, firestore)
    }

    @Provides
    @Singleton
    fun provideCredentialsManager(
    ): GoogleClientManager {
        return GoogleClientManager()
    }
}