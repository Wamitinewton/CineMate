package com.newton.database.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.newton.database.dao.TmdbUserDao
import com.newton.database.entity.TmdbUserEntity

@Database(
    entities = [TmdbUserEntity::class],
    version = 1,
    exportSchema = true
)
abstract class CineMateDatabase : RoomDatabase() {

    abstract fun tmdbUserDao(): TmdbUserDao

    companion object {
        const val DATABASE_NAME = "cine_mate_database"
    }
}