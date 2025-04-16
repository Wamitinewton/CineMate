package com.newton.auth.data

import com.google.firebase.auth.*
import com.google.firebase.firestore.*
import com.newton.auth.data.mapper.*
import com.newton.core.utils.*
import com.newton.domain.models.User
import com.newton.domain.repository.AuthRepository
import com.newton.network.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.tasks.*
import javax.inject.*

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AuthRepository {
    override fun isUserAuthenticated(): Boolean =
        firebaseAuth.currentUser != null


    override fun getCurrentUser(): User? =
        firebaseAuth.currentUser?.toUser()


    override suspend fun signInWithGoogle(idToken: String): Flow<Resource<User>> = safeApiCall(
        apiCall = {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val authResult = firebaseAuth.signInWithCredential(credential).await()
            val user = authResult.user?.toUser()

            user ?: throw Exception("User authentication failed")

            saveUserData(user)

            user
        }
    )

    override suspend fun saveUserData(user: User) {
        try {
            user.uid.let { userId ->
                val userDocument =
                    firestore.collection(FirebaseCollections.USERS_COLLECTION).document(userId)

                val userData = mapOf(
                    "uid" to user.uid,
                    "email" to user.email,
                    "displayName" to user.displayName,
                    "photoUrl" to user.photoUrl,
                    "lastSignIn" to System.currentTimeMillis()
                )

                userDocument.set(userData, SetOptions.merge()).await()
            }
        } catch (e: Exception) {

        }
    }

    override suspend fun signOut(): Flow<Resource<Unit>> = safeApiCall(
        apiCall = {
            firebaseAuth.signOut()
        }
    )


}