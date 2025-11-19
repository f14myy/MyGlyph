package com.f14my.myglyph.ui.theme

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Nothing OS стиль - темная тема с красными акцентами
private val NothingDarkColorScheme = darkColorScheme(
    primary = NothingRed,
    onPrimary = NothingWhite,
    primaryContainer = NothingDarkGray,
    onPrimaryContainer = NothingWhite,
    
    secondary = NothingGray,
    onSecondary = NothingWhite,
    secondaryContainer = NothingDarkGray,
    onSecondaryContainer = NothingLightGray,
    
    tertiary = NothingRed,
    onTertiary = NothingWhite,
    
    background = NothingBlack,
    onBackground = NothingWhite,
    
    surface = NothingBlack,
    onSurface = NothingWhite,
    surfaceVariant = NothingGray,
    onSurfaceVariant = NothingLightGray,
    
    outline = NothingGray,
    outlineVariant = NothingDarkGray,
    
    error = NothingRed,
    onError = NothingWhite
)

// Светлая тема (на случай если понадобится)
private val NothingLightColorScheme = lightColorScheme(
    primary = NothingRed,
    onPrimary = NothingWhite,
    primaryContainer = NothingLightGray,
    onPrimaryContainer = NothingBlack,
    
    secondary = NothingLightGray,
    onSecondary = NothingBlack,
    secondaryContainer = NothingLightGray,
    onSecondaryContainer = NothingBlack,
    
    tertiary = NothingRed,
    onTertiary = NothingWhite,
    
    background = NothingWhite,
    onBackground = NothingBlack,
    
    surface = NothingWhite,
    onSurface = NothingBlack,
    surfaceVariant = NothingLightGray,
    onSurfaceVariant = NothingGray,
    
    outline = NothingGray,
    outlineVariant = NothingLightGray,
    
    error = NothingRed,
    onError = NothingWhite
)

@Composable
fun GlyphalwTheme(
    themeMode: ThemeMode = ThemeMode.DARK,
    content: @Composable () -> Unit
) {
    val isDarkTheme = when (themeMode) {
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
    }
    
    val colorScheme = if (isDarkTheme) {
        NothingDarkColorScheme
    } else {
        NothingLightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !isDarkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
