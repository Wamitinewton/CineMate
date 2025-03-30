package com.newton.auth.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.newton.auth.data.mapper.toUser
import com.newton.core.utils.FirebaseCollections
import com.newton.core.utils.Resource
import com.newton.network.domain.models.User
import com.newton.network.domain.repositories.AuthRepository
import com.newton.network.safeApiCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

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
                val userDocument = firestore.collection(FirebaseCollections.USERS_COLLECTION).document(userId)

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