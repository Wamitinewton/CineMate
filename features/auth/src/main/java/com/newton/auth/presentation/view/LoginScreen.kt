package com.newton.auth.presentation.view

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
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
import androidx.compose.ui.unit.*
import androidx.lifecycle.compose.*
import com.newton.auth.presentation.viewModel.*
import com.newton.shared_ui.sharedComponents.*
import com.newton.shared_ui.theme.*
import kotlinx.coroutines.*

@Composable
fun OnboardingScreen(
    onAuthSuccess: () -> Unit,
    onContinueWithoutAccountClick: () -> Unit,
    viewModel: AuthViewModel,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    // Use rememberCoroutineScope instead of lifecycleScope
    val coroutineScope = rememberCoroutineScope()

    var visible by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                coroutineScope.launch {
                    result.data?.let { intent ->
                        viewModel.handleSignInIntent(intent)
                    }
                }
            }
        }
    )

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

                // App Title and Description
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
                        text = if (state.isLoading) "Signing in..." else "Continue with Google",
                        onClick = {
                            coroutineScope.launch {
                                val signInIntentSender = viewModel.signIn()
                                signInIntentSender?.let {
                                    launcher.launch(
                                        IntentSenderRequest.Builder(signInIntentSender).build()
                                    )
                                }
                            }
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