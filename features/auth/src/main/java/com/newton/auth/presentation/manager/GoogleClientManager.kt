package com.newton.auth.presentation.manager

import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Manager class responsible for Google authentication operations
 * using the Credential Manager API
 */
@Singleton
class GoogleClientManager @Inject constructor() {

    /**
     * Creates and returns a Google sign-in credential request
     *
     * @param webClientId Google OAuth web client ID
     * @return Configured credential request
     */
    fun createGoogleSignInRequest(webClientId: String): GetCredentialRequest {
        val googleIdOption = GetGoogleIdOption.Builder()
            .setServerClientId(webClientId)
            .setFilterByAuthorizedAccounts(false)
            .setAutoSelectEnabled(false)
            .build()

        return GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()
    }

    /**
     * Process the credential response from Google Sign-in
     *
     * @param response The credential response from Credential Manager
     * @return Flow with processed result containing ID token or error
     */
    fun processCredentialResponse(response: GetCredentialResponse): Flow<GoogleSignInResult> = flow {
        emit(GoogleSignInResult.Loading)

        try {
            when (val credential = response.credential) {
                is CustomCredential -> {
                    if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                        val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                        val idToken = googleIdTokenCredential.idToken
                        Timber.d("Successfully processed Google credential")
                        emit(GoogleSignInResult.Success(idToken))
                    } else {
                        Timber.e("Unsupported credential type: ${credential.type}")
                        emit(GoogleSignInResult.Error("Unsupported credential type"))
                    }
                }
                else -> {
                    Timber.e("Unexpected credential type")
                    emit(GoogleSignInResult.Error("Unexpected credential type"))
                }
            }
        } catch (e: Exception) {
            Timber.e(e, "Failed to process credential")
            emit(GoogleSignInResult.Error("Failed to process credential", e))
        }
    }.flowOn(Dispatchers.IO)

    /**
     * Handle credential error
     *
     * @param exception The exception from credential manager
     * @return Flow with error result
     */
    fun handleCredentialError(exception: GetCredentialException): Flow<GoogleSignInResult> = flow {
        Timber.e(exception, "Authentication failed")
        emit(GoogleSignInResult.Error("Authentication failed", exception))
    }.flowOn(Dispatchers.IO)
}

/**
 * Represents the result of Google sign-in operations
 */
sealed class GoogleSignInResult {
    data object Loading : GoogleSignInResult()
    data class Success(val idToken: String) : GoogleSignInResult()
    data class Error(val message: String, val exception: Exception? = null) : GoogleSignInResult()
}