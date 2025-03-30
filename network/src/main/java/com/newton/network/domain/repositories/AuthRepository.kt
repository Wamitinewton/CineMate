package com.newton.network.domain.repositories

import com.newton.core.utils.Resource
import com.newton.network.domain.models.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun isUserAuthenticated(): Boolean

    fun getCurrentUser(): User?

    suspend fun signInWithGoogle(idToken: String): Flow<Resource<User>>

    suspend fun saveUserData(user: User)

    suspend fun signOut(): Flow<Resource<Unit>>
}