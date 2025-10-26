package com.f14my.glyphalw

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

// Запечатанный класс для наших экранов
sealed class Screen(val route: String, val label: String, val icon: ImageVector) {
    object Home : Screen("home", "Glyph controls", Icons.Default.Home)
    object Tools : Screen("tools", "Glyph tools", Icons.Default.Build)
    object About : Screen("about", "About", Icons.Default.Info)
}

// Список экранов для удобства
val bottomNavItems = listOf(
    Screen.Home,
    Screen.Tools,
    Screen.About
)