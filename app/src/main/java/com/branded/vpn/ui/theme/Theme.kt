package com.branded.vpn.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryIndigo,
    secondary = SecondaryIndigo,
    tertiary = AccentTeal,
    background = BackgroundDeep,
    surface = SurfaceDeep,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = TextWhite,
    onSurface = TextWhite,
    surfaceVariant = SurfaceDeep.copy(alpha = 0.7f),
    onSurfaceVariant = TextGray
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryIndigo,
    secondary = SecondaryIndigo,
    tertiary = AccentTeal,
    background = BackgroundOffWhite,
    surface = SurfaceWhite,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = TextTitle,
    onSurface = TextTitle,
    surfaceVariant = Color(0xFFF3F4F6),
    onSurfaceVariant = TextBody
)

@Composable
fun BrandedVpnTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
