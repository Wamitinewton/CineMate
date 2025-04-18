package com.newton.shared_ui.sharedComponents

import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.*

@Composable
fun SearchCard(
    modifier: Modifier = Modifier,
    onSearchClick: () -> Unit
) {
    val colors = listOf(
        MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
        MaterialTheme.colorScheme.tertiary.copy(alpha = 0.4f),
        MaterialTheme.colorScheme.secondary.copy(alpha = 0.7f)
    )

    val infiniteTransition = rememberInfiniteTransition(label = "gradientTransition")
    val offset = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "gradientAnimation"
    )

    val brush = Brush.linearGradient(
        colors = colors,
        start = Offset(offset.value * 300, 0f),
        end = Offset(offset.value * 300 + 300, 300f)
    )

    val searchText = "Search..."
    val letterCount = searchText.length

    val boldIndex = infiniteTransition.animateValue(
        initialValue = 0,
        targetValue = letterCount,
        typeConverter = Int.VectorConverter,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "textBoldAnimation"
    )

    val glowAlpha = infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 0.9f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glowAnimation"
    )

    Card(
        modifier = modifier
            .height(40.dp)
            .shadow(
                elevation = 4.dp,
                shape = MaterialTheme.shapes.extraLarge,
                spotColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
            )
            .clip(MaterialTheme.shapes.extraLarge)
            .clickable { onSearchClick() },
        shape = MaterialTheme.shapes.extraLarge,
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(brush)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = MaterialTheme.colorScheme.onPrimary.copy(alpha = glowAlpha.value),
                    modifier = Modifier.padding(end = 8.dp)
                )

                Text(
                    text = AnnotatedString.Builder().apply {
                        searchText.forEachIndexed { index, char ->
                            val isBold = index == boldIndex.value

                            append(
                                AnnotatedString(
                                    char.toString(),
                                    SpanStyle(
                                        fontWeight = if (isBold) FontWeight.ExtraBold else FontWeight.Light,
                                        fontSize = if (isBold) 16.sp else 14.sp,
                                        color = MaterialTheme.colorScheme.onPrimary.copy(
                                            alpha = if (isBold) 1f else 0.8f
                                        ),
                                        shadow = if (isBold) Shadow(
                                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f),
                                            offset = Offset(0f, 0f),
                                            blurRadius = 8f
                                        ) else null
                                    )
                                )
                            )
                        }
                    }.toAnnotatedString(),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}