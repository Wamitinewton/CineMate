package com.newton.database.dao

import androidx.room.*
import com.newton.database.entity.TmdbUserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TmdbUserDao {

    @Query("SELECT * FROM tmdb_users WHERE user_id = :userId LIMIT 1")
    suspend fun getUserById(userId: Int): TmdbUserEntity?

    @Query("SELECT * FROM tmdb_users LIMIT 1")
    suspend fun getCurrentUser(): TmdbUserEntity?

    @Query("SELECT * FROM tmdb_users LIMIT 1")
    fun getCurrentUserFlow(): Flow<TmdbUserEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: TmdbUserEntity)

    @Update
    suspend fun updateUser(user: TmdbUserEntity)

    @Query("DELETE FROM tmdb_users")
    suspend fun clearAllUsers()

    @Query("DELETE FROM tmdb_users WHERE user_id = :userId")
    suspend fun deleteUserById(userId: Int)

    @Query("SELECT COUNT(*) FROM tmdb_users")
    suspend fun getUserCount(): Int
}
