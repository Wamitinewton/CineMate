package com.newton.cinemate.application

import android.app.Application
import androidx.credentials.CredentialManager
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CineMateApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)
    }
}