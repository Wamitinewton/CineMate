package com.newton.auth.presentation.view

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*
import com.newton.shared_ui.theme.*
import kotlinx.coroutines.*

@Composable
fun CinematicOnboardingText() {
    var pulseState by remember { mutableStateOf(false) }
    val pulseFactor by animateFloatAsState(
        targetValue = if (pulseState) 1.05f else 1f,
        animationSpec = tween(800),
        label = "pulseFactor"
    )

    LaunchedEffect(Unit) {
        while (true) {
            delay(2000)
            pulseState = !pulseState
            delay(2000)
        }
    }

    val testGradient = Brush.linearGradient(
        colors = listOf(
            Color.White,
            dark_secondary.copy(alpha = 0.8f),
            Color.White
        )
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(vertical = 32.dp)
    ) {
        Text(
            text = "CineVerse",
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.ExtraBold,
                fontSize = 38.sp,
                shadow = Shadow(
                    color = dark_secondary.copy(alpha = 0.7f),
                    offset = Offset(2f, 2f),
                    blurRadius = 4f
                )
            ),
            color = Color.White,
            modifier = Modifier
                .scale(pulseFactor)
                .graphicsLayer {
                    alpha = 0.99f
                }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Unlimited movies, shows, and more\nright at your fingertips!",
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                lineHeight = 26.sp,
                shadow = Shadow(
                    color = Color.Black.copy(alpha = 0.7f),
                    offset = Offset(1f, 1f),
                    blurRadius = 2f
                )
            ),
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.9f),
            textAlign = TextAlign.Center
        )
    }
}