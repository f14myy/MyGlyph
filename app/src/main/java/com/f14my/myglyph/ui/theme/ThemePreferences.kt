package com.f14my.myglyph.ui.theme

import android.content.Context
import androidx.compose.runtime.compositionLocalOf
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Режимы темы
enum class ThemeMode {
    SYSTEM,  // Следовать системной теме
    LIGHT,   // Всегда светлая
    DARK     // Всегда темная
}

// DataStore для хранения настроек темы
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "theme_settings")

class ThemePreferences(private val context: Context) {
    
    companion object {
        private val THEME_MODE_KEY = stringPreferencesKey("theme_mode")
    }
    
    // Получить текущий режим темы
    val themeModeFlow: Flow<ThemeMode> = context.dataStore.data.map { preferences ->
        val themeModeString = preferences[THEME_MODE_KEY] ?: ThemeMode.DARK.name
        try {
            ThemeMode.valueOf(themeModeString)
        } catch (e: IllegalArgumentException) {
            ThemeMode.DARK
        }
    }
    
    // Сохранить режим темы
    suspend fun setThemeMode(mode: ThemeMode) {
        context.dataStore.edit { preferences ->
            preferences[THEME_MODE_KEY] = mode.name
        }
    }
}

// CompositionLocal для доступа к ThemePreferences
val LocalThemePreferences = compositionLocalOf<ThemePreferences> {
    error("ThemePreferences not provided")
}
