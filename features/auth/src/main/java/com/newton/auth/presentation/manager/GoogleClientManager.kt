package com.newton.auth.presentation.manager

import android.content.Context
import androidx.credentials.*
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.GetCredentialInterruptedException
import androidx.credentials.exceptions.GetCredentialProviderConfigurationException
import androidx.credentials.exceptions.GetCredentialUnsupportedException
import androidx.credentials.exceptions.NoCredentialException
import com.google.android.libraries.identity.googleid.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GoogleClientManager @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {
    private var credentialManager: CredentialManager? = null

    fun initializeGoogleSignIn(context: Context) {
        credentialManager = CredentialManager.create(context)
        Timber.d("Google Sign-In initialized with Credential Manager")
    }

    fun createGoogleSignInRequest(): GetCredentialRequest {
        val googleIdOption = GetGoogleIdOption.Builder()
            .setServerClientId("989952666104-4gb7rdtrb0417k7p3rd60fphmakq6f53.apps.googleusercontent.com")
            .setFilterByAuthorizedAccounts(false)
            .setAutoSelectEnabled(false)
            .build()

        return GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()
    }

    fun performGoogleSignIn(context: Context): Flow<GoogleSignInResult> = flow {
        emit(GoogleSignInResult.Loading)

        try {
            val credentialManager = CredentialManager.create(context)
            val request = createGoogleSignInRequest()

            val result = credentialManager.getCredential(
                request = request,
                context = context
            )

            handleCredentialResponse(result).collect { signInResult ->
                emit(signInResult)
            }

        } catch (e: GetCredentialException) {
            Timber.e(e, "Google Sign-In failed")
            Timber.e(e.message)
            val errorMessage = when (e) {
                is NoCredentialException -> "No Google accounts found. Please add a Google account in Settings."
                is GetCredentialCancellationException -> "Sign-in was cancelled"
                is GetCredentialInterruptedException -> "Sign-in was interrupted. Please try again."
                is GetCredentialProviderConfigurationException -> "Google Sign-in is not properly configured."
                is GetCredentialUnsupportedException -> "Google Sign-in is not supported on this device."
                else -> "Sign-in failed: ${e.message}"
            }
            emit(GoogleSignInResult.Error(errorMessage))
        } catch (e: Exception) {
            Timber.e(e, "Unexpected error during sign-in")
            emit(GoogleSignInResult.Error("Sign-in failed: ${e.message}"))
        }
    }

    private fun handleCredentialResponse(response: GetCredentialResponse): Flow<GoogleSignInResult> = flow {
        try {
            when (val credential = response.credential) {
                is CustomCredential -> {
                    if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                        val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                        val idToken = googleIdTokenCredential.idToken

                        Timber.d("Google ID token received, authenticating with Firebase...")

                        val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
                        val authResult = firebaseAuth.signInWithCredential(firebaseCredential).await()

                        if (authResult.user != null) {
                            Timber.d("Firebase authentication successful")
                            emit(GoogleSignInResult.Success(authResult.user!!))
                        } else {
                            emit(GoogleSignInResult.Error("Firebase authentication failed"))
                        }
                    } else {
                        emit(GoogleSignInResult.Error("Unsupported credential type"))
                    }
                }
                else -> {
                    emit(GoogleSignInResult.Error("Unexpected credential type"))
                }
            }
        } catch (e: Exception) {
            Timber.e(e, "Failed to process credential response")
            emit(GoogleSignInResult.Error("Authentication failed: ${e.message}"))
        }
    }

    fun signOut(): Flow<GoogleSignInResult> = flow {
        try {
            firebaseAuth.signOut()
            emit(GoogleSignInResult.Success(null))
        } catch (e: Exception) {
            emit(GoogleSignInResult.Error("Sign-out failed: ${e.message}"))
        }
    }

    fun isUserSignedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }

    fun getCurrentUser() = firebaseAuth.currentUser
}

sealed class GoogleSignInResult {
    object Loading : GoogleSignInResult()
    data class Success(val user: com.google.firebase.auth.FirebaseUser?) : GoogleSignInResult()
    data class Error(val message: String) : GoogleSignInResult()
}