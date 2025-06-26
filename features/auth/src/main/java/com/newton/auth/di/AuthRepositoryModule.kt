package com.newton.auth.di

import com.google.firebase.auth.*
import com.google.firebase.firestore.*
import com.newton.auth.data.*
import com.newton.auth.presentation.manager.*
import com.newton.domain.repository.AuthRepository
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

}