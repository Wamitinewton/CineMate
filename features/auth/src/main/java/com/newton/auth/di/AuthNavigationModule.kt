package com.newton.auth.di

import com.newton.auth.navigation.AuthNavigationApi
import com.newton.auth.navigation.AuthNavigationImpl
import com.newton.prefs.PrefsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthNavigationModule {

    @Provides
    @Singleton
    fun provideAuthNavigationApi(
        prefsRepository: PrefsRepository
    ): AuthNavigationApi {
        return AuthNavigationImpl(prefsRepository)
    }
}