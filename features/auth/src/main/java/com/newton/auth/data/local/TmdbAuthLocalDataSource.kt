package com.newton.auth.data.local

import com.newton.database.dao.TmdbUserDao
import com.newton.database.mapper.toDomain
import com.newton.database.mapper.toEntity
import com.newton.domain.models.TmdbUser
import com.newton.prefs.PreferenceManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TmdbAuthLocalDataSource @Inject constructor(
    private val preferenceManager: PreferenceManager,
    private val tmdbUserDao: TmdbUserDao
) {

    companion object {
        private const val KEY_SESSION_ID = "tmdb_session_id"
    }


    suspend fun saveUserData(user: TmdbUser, sessionId: String) {
        preferenceManager.putString(KEY_SESSION_ID, sessionId)

        tmdbUserDao.insertUser(user.toEntity())
    }


    suspend fun getCurrentUser(): TmdbUser? {
        return tmdbUserDao.getCurrentUser()?.toDomain()
    }


    fun getCurrentUserFlow(): Flow<TmdbUser?> {
        return tmdbUserDao.getCurrentUserFlow().map { entity ->
            entity?.toDomain()
        }
    }

    /**
     * Retrieves session ID from preferences
     */
    fun getSessionId(): String? {
        val sessionId = preferenceManager.getString(KEY_SESSION_ID)
        return sessionId.ifEmpty { null }
    }

    /**
     * Updates existing user data in database
     */
    suspend fun updateUserData(user: TmdbUser) {
        tmdbUserDao.updateUser(user.toEntity())
    }

    /**
     * Retrieves user by ID from database
     */
    suspend fun getUserById(userId: Int): TmdbUser? {
        return tmdbUserDao.getUserById(userId)?.toDomain()
    }

    /**
     * Checks if any user data exists in database
     */
    suspend fun hasUserData(): Boolean {
        return tmdbUserDao.getUserCount() > 0
    }


    suspend fun clearUserData() {
        preferenceManager.putString(KEY_SESSION_ID, "")

        tmdbUserDao.clearAllUsers()
    }


    fun clearSessionData() {
        preferenceManager.putString(KEY_SESSION_ID, "")
    }
}