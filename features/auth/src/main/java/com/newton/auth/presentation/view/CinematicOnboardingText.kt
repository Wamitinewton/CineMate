package com.newton.auth.presentation.view

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.newton.shared_ui.theme.dark_secondary
import kotlinx.coroutines.delay

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
            text =  "Unlimited movies, shows, and more\nright at your fingertips!",
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