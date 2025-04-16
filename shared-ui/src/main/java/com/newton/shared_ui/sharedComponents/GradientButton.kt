package com.newton.shared_ui.sharedComponents


import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.ripple.*
import androidx.compose.material3.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.*
import com.newton.shared_ui.theme.*

@Composable
fun CustomButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    variant: ButtonVariant = ButtonVariant.FILLED,
    size: ButtonSize = ButtonSize.MEDIUM,
    buttonColors: ButtonColors = com.newton.shared_ui.sharedComponents.ButtonDefaults.defaultMaterialColors(),
    cornerRadius: Dp = com.newton.shared_ui.sharedComponents.ButtonDefaults.cornerRadius,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    isLoading: Boolean = false,
    enableAnimation: Boolean = true,
    gradientColors: List<Color>? = null,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val buttonHeight = when (size) {
        ButtonSize.SMALL -> 36.dp
        ButtonSize.MEDIUM -> 48.dp
        ButtonSize.LARGE -> 56.dp
    }

    val scale by animateFloatAsState(
        targetValue = if (isPressed && enableAnimation) 0.96f else 1f,
        label = "buttonScale"
    )

    val shape = RoundedCornerShape(cornerRadius)

    val backgroundColor = when (variant) {
        ButtonVariant.FILLED -> if (enabled) buttonColors.containerColor else buttonColors.disabledContainerColor
        else -> Color.Transparent
    }

    val contentColor = when (variant) {
        ButtonVariant.FILLED -> if (enabled) buttonColors.contentColor else buttonColors.disabledContentColor
        else -> if (enabled) buttonColors.containerColor else buttonColors.disabledContainerColor
    }

    val buttonModifier = Modifier
        .then(modifier)
        .height(buttonHeight)
        .scale(scale)
        .clip(shape)
        .then(
            if (variant == ButtonVariant.OUTLINED) {
                Modifier.border(1.dp, contentColor, shape)
            } else {
                Modifier
            }
        )
        .then(
            if (variant == ButtonVariant.FILLED) {
                Modifier.shadow(
                    elevation = if (enabled) 2.dp else 0.dp,
                    shape = shape
                )
            } else {
                Modifier
            }
        )
        .then(
            if (gradientColors != null && variant == ButtonVariant.FILLED) {
                Modifier.background(
                    Brush.linearGradient(colors = gradientColors),
                    shape
                )
            } else {
                Modifier.background(backgroundColor, shape)
            }
        )
        .clickable(
            interactionSource = interactionSource,
            indication = rememberRipple(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)),
            enabled = enabled,
            onClick = onClick
        )
        .padding(horizontal = 16.dp)

    Box(
        modifier = buttonModifier,
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = contentColor,
                    strokeWidth = 2.dp
                )
            } else {
                leadingIcon?.let {
                    Icon(
                        imageVector = it,
                        contentDescription = null,
                        tint = contentColor
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }

                Text(
                    text = text,
                    color = contentColor,
                    fontWeight = FontWeight.Medium,
                    style = MaterialTheme.typography.labelLarge
                )

                trailingIcon?.let {
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = it,
                        contentDescription = null,
                        tint = contentColor
                    )
                }
            }
        }
    }
}

enum class ButtonVariant {
    FILLED,
    OUTLINED,
    MINIMAL
}

enum class ButtonSize {
    SMALL,
    MEDIUM,
    LARGE
}

data class ButtonColors(
    val containerColor: Color,
    val contentColor: Color,
    val disabledContainerColor: Color,
    val disabledContentColor: Color
)

object ButtonDefaults {
    val cornerRadius = 8.dp

    @Composable
    fun defaultMaterialColors(
        containerColor: Color = MaterialTheme.colorScheme.primary,
        contentColor: Color = MaterialTheme.colorScheme.onPrimary,
        disabledContainerColor: Color = MaterialTheme.colorScheme.surfaceVariant,
        disabledContentColor: Color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
    ) = ButtonColors(
        containerColor = containerColor,
        contentColor = contentColor,
        disabledContainerColor = disabledContainerColor,
        disabledContentColor = disabledContentColor
    )

    @Composable
    fun primaryGradient() = listOf(
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.primaryContainer
    )

    @Composable
    fun secondaryGradient() = listOf(
        MaterialTheme.colorScheme.secondary,
        MaterialTheme.colorScheme.secondary.copy(alpha = 0.7f)
    )

    @Composable
    fun accentGradient() = listOf(
        MaterialTheme.colorScheme.secondary,
        MaterialTheme.colorScheme.primary
    )

    val darkThemeGradient = listOf(
        Color(0xFF180E36),
        Color(0xFF2B2E5B),
        Color(0xFF180E36)
    )

    val accentGradient = listOf(
        Color(0xFFFF4D00),
        Color(0xFF2B2E5B),
        Color(0xFF180E36)
    )
}

@Composable
fun GradientButton(
    buttonText: String,
    onClick: () -> Unit
) {
    val isHovered by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isHovered) 1.05f else 1f,
        animationSpec = tween(200),
        label = "buttonScale"
    )

    val infiniteTransition = rememberInfiniteTransition(label = "buttonGlow")
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.6f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glowAlpha"
    )

    val buttonGradient = Brush.linearGradient(
        colors = listOf(
            dark_secondary,
            dark_primary,
            dark_primaryContainer
        )
    )

    Button(
        onClick = {
            onClick()
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .scale(scale)
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(16.dp),
                spotColor = dark_secondary.copy(alpha = glowAlpha)
            )
            .graphicsLayer {
                clip = true
                shape = RoundedCornerShape(16.dp)
            }
            .background(buttonGradient)
            .padding(horizontal = 8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(
            text = buttonText,
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                letterSpacing = 0.5.sp
            ),
            color = Color.White,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}