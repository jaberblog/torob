package com.example.torobui.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val TorobRed = Color(0xFFD73948)
val TorobRedDark = Color(0xFFB82937)
val TorobRedLight = Color(0xFFFFEBEE)
val TorobBackground = Color(0xFFF5F6F8)
val TorobSurface = Color(0xFFFFFFFF)
val TorobTextPrimary = Color(0xFF1F2328)
val TorobTextSecondary = Color(0xFF656D76)
val TorobBorder = Color(0xFFE5E7EB)
val TorobGreenBadge = Color(0xFF1A7F37)

private val LightColors = lightColorScheme(
    primary = TorobRed,
    onPrimary = Color.White,
    primaryContainer = TorobRedLight,
    onPrimaryContainer = TorobRedDark,
    background = TorobBackground,
    onBackground = TorobTextPrimary,
    surface = TorobSurface,
    onSurface = TorobTextPrimary,
    surfaceVariant = Color(0xFFF0F1F3),
    onSurfaceVariant = TorobTextSecondary,
    outline = TorobBorder
)

@Composable
fun TorobUITheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColors,
        typography = AppTypography,
        content = content
    )
}
