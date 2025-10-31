package com.rickandmortyapi.ui.theme


import androidx.compose.material3.Typography

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


private val LightSemanticColors = SemanticColors(
    primary = Color(0xFF4CAF50),
    secondary = Color(0xFF8BC34A),
    background = Color(0xFFFFFFFF),
    surface = Color(0xFF000000),
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White,

    // Text
    text = Color.Black,
    error = Color(0xFFCF0000),
)

private val DarkSemanticColors = SemanticColors(
    primary = Color(0xFF4CAF50),
    secondary = Color(0xFF8BC34A),
    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E),
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White,

    // Text
    text = Color.White,
    error = Color(0xFFCF6679),
)

val LocalSemanticColors = staticCompositionLocalOf { LightSemanticColors }

private val DarkColorScheme = darkColorScheme(
    primary = DarkSemanticColors.primary,
    secondary = DarkSemanticColors.secondary,
    background = DarkSemanticColors.background,
    surface = DarkSemanticColors.surface,
    onPrimary = DarkSemanticColors.onPrimary,
    onSecondary = DarkSemanticColors.onSecondary,
    onBackground = DarkSemanticColors.onBackground,
    onSurface = DarkSemanticColors.onSurface,
    error = DarkSemanticColors.error
)

private val LightColorScheme = lightColorScheme(
    primary = LightSemanticColors.primary,
    secondary = LightSemanticColors.secondary,
    background = LightSemanticColors.background,
    surface = LightSemanticColors.surface,
    onPrimary = LightSemanticColors.onPrimary,
    onSecondary = LightSemanticColors.onSecondary,
    onBackground = LightSemanticColors.onBackground,
    onSurface = LightSemanticColors.onSurface,
    error = LightSemanticColors.error
)

@Composable
fun RickAndMortyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorScheme else LightColorScheme

    val typographyInstance = Typography(
        bodyLarge = TextStyle(
            color = colors.onBackground,
            fontSize = 16.sp
        ),
        titleLarge = TextStyle(
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
        ),
    )

    MaterialTheme(
        colorScheme = colors,
        typography = typographyInstance,
        content = content
    )
}
