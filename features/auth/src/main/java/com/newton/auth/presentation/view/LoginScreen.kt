package com.newton.auth.presentation.view

import android.content.*
import android.provider.*
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.unit.*
import androidx.credentials.*
import androidx.credentials.exceptions.*
import androidx.lifecycle.compose.*
import com.newton.auth.presentation.event.*
import com.newton.auth.presentation.viewModel.*
import com.newton.shared_ui.sharedComponents.*
import com.newton.shared_ui.theme.*
import kotlinx.coroutines.flow.*

@Composable
fun OnboardingScreen(
    onAuthSuccess: () -> Unit,
    onContinueWithoutAccountClick: () -> Unit,
    viewModel: AuthViewModel,
) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit) {
        visible = true
    }

    LaunchedEffect(Unit) {
        viewModel.authEvents.collectLatest { event ->
            when (event) {
                is AuthUiEvent.LaunchCredentialManager -> {
                    try {
                        val credentialManager = CredentialManager.create(context)

                        val result = credentialManager.getCredential(
                            request = event.request,
                            context = context
                        )
                        viewModel.handleCredentialResult(result)
                    } catch (e: NoCredentialException) {
                        context.startActivity(Intent(Settings.ACTION_ADD_ACCOUNT).apply {
                            putExtra(Settings.EXTRA_ACCOUNT_TYPES, arrayOf("com.google"))
                        })
                    } catch (e: GetCredentialException) {
                        viewModel.handleCredentialError(e)
                    }
                }
            }
        }
    }

    LaunchedEffect(state.error) {
        state.error?.let { error ->
            snackbarHostState.showSnackbar(error)
            viewModel.onEvent(AuthEvent.DismissError)
        }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) }
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

                androidx.compose.animation.AnimatedVisibility(
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
                androidx.compose.animation.AnimatedVisibility(
                    visible = visible,
                    enter = fadeIn(tween(1000, delayMillis = 400))
                ) {
                    CustomButton(
                        text = "Continue",
                        onClick = { onContinueWithoutAccountClick() },
                        modifier = Modifier.fillMaxWidth(),
                        variant = ButtonVariant.FILLED
                    )
                }
                Spacer(modifier = Modifier.height(40.dp))

            }
        }
    }
}
