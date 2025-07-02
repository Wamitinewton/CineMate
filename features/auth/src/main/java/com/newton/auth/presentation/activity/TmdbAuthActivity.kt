package com.newton.auth.presentation.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.newton.auth.presentation.viewModel.TmdbAuthViewModel
import com.newton.core.utils.TmdbConstants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class TmdbAuthActivity : ComponentActivity() {

    private val viewModel: TmdbAuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        val data = intent.data

        if (data != null && isAuthCallback(data)) {
            handleAuthCallback(data)
        } else {
            // Start authentication flow
            startTmdbAuthentication()
        }
    }

    private fun isAuthCallback(uri: Uri): Boolean {
        return uri.scheme == TmdbConstants.TMDB_AUTH_CALLBACK_SCHEME &&
                uri.host == TmdbConstants.TMDB_AUTH_CALLBACK_HOST
    }

    private fun handleAuthCallback(uri: Uri) {
        Timber.d("Auth callback received: $uri")

        val approved = uri.getQueryParameter("approved") ?: "true"

        if (approved == "true") {
            lifecycleScope.launch {
                viewModel.completeAuthentication()
                finishWithResult(true)
            }
        } else {
            finishWithResult(false, "Authentication was denied")
        }
    }

    private fun startTmdbAuthentication() {
        lifecycleScope.launch {
            viewModel.startAuthentication { intent ->
                if (intent != null) {
                    startActivity(intent)
                } else {
                    finishWithResult(false, "Failed to start authentication")
                }
            }
        }
    }

    private fun finishWithResult(success: Boolean, error: String? = null) {
        val resultIntent = Intent().apply {
            putExtra("auth_success", success)
            error?.let { putExtra("auth_error", it) }
        }
        setResult(if (success) RESULT_OK else RESULT_CANCELED, resultIntent)
        finish()
    }

    companion object {
        const val REQUEST_CODE_TMDB_AUTH = 1001
    }
}