package com.teenbudget.pocketmoneymanager.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = NeonTeal,
    secondary = ElectricPurple,
    tertiary = CoralOrange,
    background = DarkBackground,
    surface = DarkSurface,
    onPrimary = DarkBackground,
    onSecondary = DarkBackground,
    onTertiary = DarkBackground,
    onBackground = DarkOnSurface,
    onSurface = DarkOnSurface,
)

private val LightColorScheme = lightColorScheme(
    primary = CyberBlue,
    secondary = ElectricPurple,
    tertiary = CoralOrange,
    background = LightBackground,
    surface = LightSurface,
    onPrimary = LightBackground,
    onSecondary = LightBackground,
    onTertiary = LightBackground,
    onBackground = LightOnSurface,
    onSurface = LightOnSurface,
)

private val CyberNeonColorScheme = darkColorScheme(
    primary = NeonTeal,
    secondary = ElectricPurple,
    tertiary = NeonPink,
    background = Color(0xFF000814),
    surface = Color(0xFF001D3D),
    onPrimary = Color(0xFF000000),
    onSecondary = Color(0xFF000000),
    onTertiary = Color(0xFF000000),
    onBackground = NeonTeal,
    onSurface = Color(0xFFFFFFFF),
)

private val PastelColorScheme = lightColorScheme(
    primary = PastelBlue,
    secondary = PastelPink,
    tertiary = PastelGreen,
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFF8F9FA),
    onPrimary = Color(0xFF000000),
    onSecondary = Color(0xFF000000),
    onTertiary = Color(0xFF000000),
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
)

@Composable
fun PocketMoneyManagerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    selectedTheme: String = "CyberNeon",
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        selectedTheme == "CyberNeon" -> CyberNeonColorScheme
        selectedTheme == "PastelChill" -> PastelColorScheme
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}