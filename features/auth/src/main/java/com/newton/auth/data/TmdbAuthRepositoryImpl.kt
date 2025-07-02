package com.newton.auth.data

import com.newton.auth.data.local.TmdbAuthLocalDataSource
import com.newton.core.utils.Resource
import com.newton.domain.models.*
import com.newton.domain.repository.TmdbAuthRepository
import com.newton.network.data.dto.DeleteSessionRequest
import com.newton.network.data.mappers.*
import com.newton.network.data.remote.TmdbAuthApiService
import com.newton.network.safeApiCall
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TmdbAuthRepositoryImpl @Inject constructor(
    private val apiService: TmdbAuthApiService,
    private val localDataSource: TmdbAuthLocalDataSource
) : TmdbAuthRepository {

    override suspend fun createRequestToken(): Flow<Resource<TmdbRequestToken>> =
        safeApiCall(
            apiCall = {
                apiService.createRequestToken().toDomain()
            }
        )

    override suspend fun createSession(requestToken: String): Flow<Resource<TmdbSession>> =
        safeApiCall(
            apiCall = {
                apiService.createSession(requestToken).toDomain()
            }
        )

    override suspend fun getAccountDetails(sessionId: String): Flow<Resource<TmdbUser>> =
        safeApiCall(
            apiCall = {
                apiService.getAccountDetails(sessionId).toDomain()
            }
        )

    override suspend fun deleteSession(sessionId: String): Flow<Resource<Unit>> =
        safeApiCall(
            apiCall = {
                apiService.deleteSession(
                    DeleteSessionRequest(sessionId)
                )
                Unit
            }
        )

    override fun getCurrentUser(): TmdbUser? {
        return localDataSource.getCurrentUser()
    }

    override fun getCurrentSessionId(): String? {
        return localDataSource.getSessionId()
    }

    override fun isUserAuthenticated(): Boolean {
        return localDataSource.getSessionId() != null
    }

    override suspend fun saveUserData(user: TmdbUser, sessionId: String) {
        localDataSource.saveUserData(user, sessionId)
    }

    override suspend fun clearUserData() {
        localDataSource.clearUserData()
    }
}