package com.newton.auth.data.mapper

import com.google.firebase.auth.*
import com.newton.domain.models.User

fun FirebaseUser.toUser(): User {
    return User(
        uid = uid,
        email = email,
        displayName = displayName,
        photoUrl = photoUrl?.toString()
    )
}