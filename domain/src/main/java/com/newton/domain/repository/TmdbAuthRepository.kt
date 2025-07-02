package com.newton.domain.repository

import com.newton.core.utils.Resource
import com.newton.domain.models.*
import kotlinx.coroutines.flow.Flow

interface TmdbAuthRepository {

    suspend fun createRequestToken(): Flow<Resource<TmdbRequestToken>>

    suspend fun createSession(requestToken: String): Flow<Resource<TmdbSession>>

    suspend fun getAccountDetails(sessionId: String): Flow<Resource<TmdbUser>>

    suspend fun deleteSession(sessionId: String): Flow<Resource<Unit>>

    suspend fun getCurrentUser(): TmdbUser?

    fun getCurrentSessionId(): String?

    fun isUserAuthenticated(): Boolean

    suspend fun saveUserData(user: TmdbUser, sessionId: String)

    suspend fun clearUserData()
}