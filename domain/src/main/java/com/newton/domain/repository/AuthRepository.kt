package com.newton.domain.repository

import com.newton.core.utils.*
import com.newton.domain.models.User
import kotlinx.coroutines.flow.*

interface AuthRepository {

    fun isUserAuthenticated(): Boolean

    fun getCurrentUser(): User?

    suspend fun signInWithGoogle(idToken: String): Flow<Resource<User>>

    suspend fun saveUserData(user: User)

    suspend fun signOut(): Flow<Resource<Unit>>
}