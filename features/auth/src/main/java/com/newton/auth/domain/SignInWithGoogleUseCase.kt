package com.newton.auth.domain

import com.newton.core.utils.Resource
import com.newton.network.domain.models.User
import com.newton.network.domain.repositories.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignInWithGoogleUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(idToken: String): Flow<Resource<User>> {
        return authRepository.signInWithGoogle(idToken)
    }
}

class GetCurrentUserUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(): User? {
        return authRepository.getCurrentUser()
    }
}

class IsUserAuthenticatedUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(): Boolean {
        return authRepository.isUserAuthenticated()
    }
}

class SignOutUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Flow<Resource<Unit>> {
        return authRepository.signOut()
    }
}