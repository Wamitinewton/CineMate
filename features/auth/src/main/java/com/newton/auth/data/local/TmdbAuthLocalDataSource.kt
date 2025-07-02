package com.newton.auth.data.local

import com.newton.domain.models.TmdbUser
import com.newton.prefs.PreferenceManager
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TmdbAuthLocalDataSource @Inject constructor(
    private val preferenceManager: PreferenceManager
) {

    companion object {
        private const val KEY_SESSION_ID = "tmdb_session_id"
        private const val KEY_USER_DATA = "tmdb_user_data"
    }

    fun saveUserData(user: TmdbUser, sessionId: String) {
        preferenceManager.putString(KEY_SESSION_ID, sessionId)
        preferenceManager.putString(KEY_USER_DATA, Json.encodeToString(user))
    }

    fun getCurrentUser(): TmdbUser? {
        val userJson = preferenceManager.getString(KEY_USER_DATA)
        return if (userJson.isNotEmpty()) {
            try {
                Json.decodeFromString<TmdbUser>(userJson)
            } catch (e: Exception) {
                null
            }
        } else {
            null
        }
    }

    fun getSessionId(): String? {
        val sessionId = preferenceManager.getString(KEY_SESSION_ID)
        return sessionId.ifEmpty { null }
    }

    fun clearUserData() {
        preferenceManager.putString(KEY_SESSION_ID, "")
        preferenceManager.putString(KEY_USER_DATA, "")
    }
}