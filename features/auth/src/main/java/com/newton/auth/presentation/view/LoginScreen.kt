package com.newton.auth.presentation.view

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.newton.auth.presentation.activity.TmdbAuthActivity
import com.newton.auth.presentation.viewModel.TmdbAuthViewModel
import com.newton.shared_ui.sharedComponents.ButtonVariant
import com.newton.shared_ui.sharedComponents.CustomButton
import com.newton.shared_ui.theme.backgroundGradient

@Composable
fun OnboardingScreen(
    onAuthSuccess: () -> Unit,
    onContinueWithoutAccountClick: () -> Unit,
    viewModel: TmdbAuthViewModel,
) {
    val state by viewModel.authState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    var visible by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        when (result.resultCode) {
            Activity.RESULT_OK -> {
                val success = result.data?.getBooleanExtra("auth_success", false) ?: false
                if (success) {
                    onAuthSuccess()
                } else {
                    val error = result.data?.getStringExtra("auth_error") ?: "Authentication failed"
                    Toast.makeText(context, error, Toast.LENGTH_LONG).show()
                }
            }
            Activity.RESULT_CANCELED -> {
                val error = result.data?.getStringExtra("auth_error") ?: "Authentication cancelled"
                Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
            }
        }
    }

    LaunchedEffect(key1 = Unit) {
        visible = true
    }

    LaunchedEffect(state.isAuthenticated) {
        if (state.isAuthenticated && state.user != null) {
            onAuthSuccess()
        }
    }

    LaunchedEffect(state.error) {
        state.error?.let { error ->
            Toast.makeText(context, error, Toast.LENGTH_LONG).show()
            viewModel.clearError()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(backgroundGradient)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Spacer(modifier = Modifier.height(48.dp))

                AnimatedVisibility(
                    visible = visible,
                    enter = fadeIn(tween(1000)) + slideInVertically(
                        initialOffsetY = { -200 },
                        animationSpec = tween(1000)
                    )
                ) {
                    OnboardingMovieCarousel()
                }

                Spacer(modifier = Modifier.height(48.dp))

                AnimatedVisibility(
                    visible = visible,
                    enter = fadeIn(tween(1000, delayMillis = 200))
                ) {
                    CinematicOnboardingText()
                }

                Spacer(modifier = Modifier.height(40.dp))

                AnimatedVisibility(
                    visible = visible,
                    enter = fadeIn(tween(1000, delayMillis = 300))
                ) {
                    CustomButton(
                        text = if (state.isLoading) "Connecting to TMDB..." else "Sign in with TMDB",
                        onClick = {
                            val intent = android.content.Intent(context, TmdbAuthActivity::class.java)
                            launcher.launch(intent)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        variant = ButtonVariant.FILLED,
                        enabled = !state.isLoading,
                        isLoading = state.isLoading,
                        leadingIcon = if (!state.isLoading) Icons.AutoMirrored.Filled.Login else null,
                        gradientColors = listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.secondary
                        )
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                AnimatedVisibility(
                    visible = visible,
                    enter = fadeIn(tween(1000, delayMillis = 400))
                ) {
                    CustomButton(
                        text = "Continue as Guest",
                        onClick = { onContinueWithoutAccountClick() },
                        modifier = Modifier.fillMaxWidth(),
                        variant = ButtonVariant.FILLED,
                        enabled = !state.isLoading,
                        gradientColors = listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.secondary
                        )
                    )
                }

                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}