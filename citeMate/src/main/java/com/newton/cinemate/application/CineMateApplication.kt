package com.newton.cinemate.application

import android.app.*
import com.google.firebase.*
import dagger.hilt.android.*

@HiltAndroidApp
class CineMateApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)
    }
}