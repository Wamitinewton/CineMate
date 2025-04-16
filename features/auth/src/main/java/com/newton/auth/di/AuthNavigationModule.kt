package com.newton.auth.di

import com.newton.auth.navigation.*
import com.newton.prefs.*
import dagger.*
import dagger.hilt.*
import dagger.hilt.components.*
import javax.inject.*

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