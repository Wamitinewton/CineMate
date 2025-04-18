package com.newton.shared_ui.sharedComponents

import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.*
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.*
import kotlin.math.*

@Composable
fun AnimatedReviewsButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "text-animation")
    val animationProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "bold-animation"
    )

    val text = "VIEW FILM REVIEWS"
    val letterCount = text.length

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.secondary,
                        MaterialTheme.colorScheme.tertiary
                    )
                ),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
        Row(
            modifier = Modifier.defaultMinSize(minWidth = 180.dp),
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            text.forEachIndexed { index, char ->
                val charPosition = index.toFloat() / letterCount
                val distanceFromAnimPoint =
                    minOf(
                        abs(charPosition - animationProgress),
                        abs(charPosition - animationProgress + 1),
                        abs(charPosition - animationProgress - 1)
                    )

                val fontWeight = when {
                    distanceFromAnimPoint < 0.05f -> FontWeight.ExtraBold
                    distanceFromAnimPoint < 0.10f -> FontWeight.Bold
                    distanceFromAnimPoint < 0.15f -> FontWeight.SemiBold
                    else -> FontWeight.Normal
                }

                Text(
                    text = char.toString(),
                    color = Color.White,
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = fontWeight,
                        fontFamily = FontFamily.Monospace,
                        shadow = Shadow(
                            color = Color.Black.copy(alpha = 0.5f),
                            offset = Offset(1f, 1f),
                            blurRadius = 2f
                        )
                    )
                )
            }

            val scale by animateFloatAsState(
                targetValue = if ((animationProgress * letterCount).toInt() % letterCount > letterCount - 3) 1.2f else 1f,
                animationSpec = spring(dampingRatio = 0.4f, stiffness = 100f),
                label = "arrow-scale"
            )

            Spacer(modifier = Modifier.width(4.dp))

            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "View Reviews",
                tint = Color.White,
                modifier = Modifier
                    .size(20.dp)
                    .scale(scale)
            )
        }
    }
}