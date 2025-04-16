package com.newton.auth.presentation.view

import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.unit.*
import coil3.compose.*
import coil3.request.*
import com.newton.shared_ui.theme.*
import kotlin.random.*


@Composable
fun OnboardingMovieCarousel() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MovieStrip(
            imageResId = com.newton.shared_ui.R.drawable.onboarding1,
            contentDescription = "Fantasy movie characters",
            rotationAngle = -8f,
            scale = 1.1f
        )

        Spacer(modifier = Modifier.height(8.dp))

        MovieStrip(
            imageResId = com.newton.shared_ui.R.drawable.onboarding2,
            contentDescription = "Sci-fi movie scene",
            rotationAngle = -8f,
            scale = 1.1f
        )

        Spacer(modifier = Modifier.height(8.dp))

        MovieStrip(
            imageResId = com.newton.shared_ui.R.drawable.onnoarding3,
            contentDescription = "Sci-fi",
            rotationAngle = -8f,
            scale = 1.1f
        )

    }
}

@Composable
fun MovieStrip(
    modifier: Modifier = Modifier,
    imageResId: Int,
    contentDescription: String,
    rotationAngle: Float,
    scale: Float
) {
    val context = LocalContext.current
    val infiniteTransition = rememberInfiniteTransition(label = "stripAnimation")
    val extraRotation by infiniteTransition.animateFloat(
        initialValue = -0.8f,
        targetValue = 0.8f,
        animationSpec = infiniteRepeatable(
            animation = tween(5000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "extraRotation"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(horizontal = 8.dp)
            .graphicsLayer {
                this.rotationX = rotationAngle + extraRotation
                this.scaleX = scale
                this.scaleY = scale
            }
            .shadow(
                elevation = 16.dp,
                shape = RoundedCornerShape(12.dp),
                spotColor = dark_primary
            )
            .clip(RoundedCornerShape(12.dp))
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(imageResId)
                .build(),
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            dark_primary.copy(alpha = 0.6f),
                            Color.Transparent,
                            dark_primary.copy(alpha = 0.6f)
                        )
                    )
                )
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            dark_secondary.copy(alpha = 0.15f),
                            Color.Transparent
                        ),
                        center = Offset(Random.nextFloat(), 0.5f),
                        radius = 0.8f
                    )
                )
        )
    }
}