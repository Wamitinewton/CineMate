package com.newton.prefs

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PreferenceModule {

    @Provides
    @Singleton
    fun providePrefsManager(
        @ApplicationContext context: Context
    ): PreferenceManager {
        return PreferenceManager(context)
    }

    @Provides
    @Singleton
    fun providePrefsRepository(
        preferenceManager: PreferenceManager
    ): PrefsRepository {
        return PrefsRepositoryImpl(preferenceManager)
    }
}