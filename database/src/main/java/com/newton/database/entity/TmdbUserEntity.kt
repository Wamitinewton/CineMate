package com.newton.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tmdb_users")
data class TmdbUserEntity(
    @PrimaryKey
    @ColumnInfo(name = "user_id")
    val id: Int,

    @ColumnInfo(name = "username")
    val username: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "avatar_path")
    val avatarPath: String?,

    @ColumnInfo(name = "include_adult")
    val includeAdult: Boolean,

    @ColumnInfo(name = "language")
    val language: String,

    @ColumnInfo(name = "country")
    val country: String,

    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "updated_at")
    val updatedAt: Long = System.currentTimeMillis()
)