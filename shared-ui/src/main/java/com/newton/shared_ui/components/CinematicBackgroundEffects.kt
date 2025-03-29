package com.newton.shared_ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.newton.shared_ui.theme.dark_secondary
import kotlin.random.Random

@Composable
fun CinematicBackgroundEffects() {
    val infiniteTransition = rememberInfiniteTransition(label = "lightBeam")
    val lightAlpha by infiniteTransition.animateFloat(
        initialValue = 0.05f,
        targetValue = 0.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "lightAlpha"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .alpha(lightAlpha)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        dark_secondary.copy(alpha = 0.4f),
                        Color.Transparent
                    )
                )
            )
            .blur(20.dp)
    )

    repeat(20) {
        val xRandom = Random.nextFloat() * 500f
        val yRandom = Random.nextFloat() * 1000f
        val particleSize = (2 + Random.nextFloat() * 6)
        val particleAlpha by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 0.6f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 2000 + (Random.nextInt(3000)),
                    easing = LinearEasing
                ),
                repeatMode = RepeatMode.Reverse
            ),
            label = "particleAlpha$it"
        )

        Box(
            modifier = Modifier
                .size(particleSize.dp)
                .alpha(particleAlpha * 0.3f)
                .background(Color.White)
                .blur(1.dp)
                .graphicsLayer {
                    translationY = yRandom
                    translationX = xRandom
                }
        )
    }
}