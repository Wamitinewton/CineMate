package com.newton.auth.presentation.view

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.newton.shared_ui.theme.dark_primary
import com.newton.shared_ui.theme.dark_secondary
import kotlin.random.Random


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