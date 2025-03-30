package com.newton.auth.presentation.view

import android.content.Intent
import android.provider.Settings
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
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
import androidx.credentials.CredentialManager
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.NoCredentialException
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.newton.auth.R
import com.newton.auth.presentation.event.AuthEvent
import com.newton.auth.presentation.event.AuthUiEvent
import com.newton.auth.presentation.viewModel.AuthViewModel
import com.newton.shared_ui.components.ButtonVariant
import com.newton.shared_ui.components.CustomButton
import com.newton.shared_ui.components.GradientButton
import com.newton.shared_ui.theme.backgroundGradient
import kotlinx.coroutines.flow.collectLatest

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
                    enter = fadeIn(tween(1500, delayMillis = 500))
                ) {
                    CinematicOnboardingText()
                }

                AnimatedVisibility(
                    visible = visible,
                    enter = fadeIn(tween(1500, delayMillis = 1000))
                ) {
                    GradientButton(
                        buttonText = "Login",
                        onClick = {
//                            viewModel.onEvent(AuthEvent.OnLoginClick(context.getString(R.string.web_client_id)))
                            /**
                             * Some issues on google auth....fixing..
                             */
                            onContinueWithoutAccountClick()
                        }
                    )
                }
                Spacer(modifier = Modifier.height(40.dp))
               androidx.compose.animation.AnimatedVisibility(
                   visible = visible,
                   enter = fadeIn(tween(1500, delayMillis = 1000))
               ) {
                   CustomButton(
                       text = "Continue with no account",
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
