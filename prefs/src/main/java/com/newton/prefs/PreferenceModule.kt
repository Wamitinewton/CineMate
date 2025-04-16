package com.newton.prefs

import android.content.*
import dagger.*
import dagger.hilt.*
import dagger.hilt.android.qualifiers.*
import dagger.hilt.components.*
import javax.inject.*

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