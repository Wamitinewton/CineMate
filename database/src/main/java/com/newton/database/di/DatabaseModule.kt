package com.newton.database.di

import android.content.Context
import androidx.room.Room
import com.newton.database.dao.TmdbUserDao
import com.newton.database.database.CineMateDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideCineMateDatabase(
        @ApplicationContext context: Context
    ): CineMateDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            CineMateDatabase::class.java,
            CineMateDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideTmdbUserDao(database: CineMateDatabase): TmdbUserDao {
        return database.tmdbUserDao()
    }
}