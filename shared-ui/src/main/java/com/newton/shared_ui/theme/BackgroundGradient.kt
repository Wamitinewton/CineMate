package com.newton.shared_ui.theme

import androidx.compose.ui.graphics.Brush

val backgroundGradient = Brush.verticalGradient(
    colors = listOf(
        dark_background,
        dark_primary.copy(alpha = 0.7f),
        dark_background
    )
)
