package com.newton.auth.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun LoginScreen() {

    val backgroundGradient = Brush.linearGradient(
        colors = listOf(Color(0xFF1A1A1A), Color(0xFF2D2D2D)),
        start = Offset(0f, 0f),
        end = Offset(1000f, 1000f)
    )

    val buttonGradient = Brush.linearGradient(
        colors = listOf(Color(0xFF00FFE0), Color(0xFFAD00FF))
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundGradient))
    {
        Text(
            text = "Auth screen"
        )
    }

}