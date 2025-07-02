package com.newton.auth.presentation.view

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.*
import androidx.lifecycle.compose.*
import com.newton.auth.presentation.activity.TmdbAuthActivity
import com.newton.auth.presentation.viewModel.TmdbAuthViewModel
import com.newton.shared_ui.sharedComponents.*
import com.newton.shared_ui.theme.*
import kotlinx.coroutines.*

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

    // Handle errors
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

                // Movie Carousel Animation
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
                        leadingIcon = if (!state.isLoading) Icons.Default.Login else null,
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
                        variant = ButtonVariant.OUTLINED,
                        enabled = !state.isLoading
                    )
                }

                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}