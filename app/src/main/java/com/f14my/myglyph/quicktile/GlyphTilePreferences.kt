package com.f14my.myglyph.quicktile

import android.content.Context
import android.content.SharedPreferences

/**
 * Класс для хранения состояния тайлов в SharedPreferences
 */
object GlyphTilePreferences {
    private const val PREFS_NAME = "glyph_tile_prefs"
    
    private const val KEY_BREATHING_ENABLED = "breathing_enabled"
    private const val KEY_EPILEPSY_ENABLED = "epilepsy_enabled"
    private const val KEY_ANIMATION_ENABLED = "animation_enabled"
    private const val KEY_TORCH_ENABLED = "torch_enabled"
    
    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }
    
    // Breathing
    fun isBreathingEnabled(context: Context): Boolean {
        return getPrefs(context).getBoolean(KEY_BREATHING_ENABLED, false)
    }
    
    fun setBreathingEnabled(context: Context, enabled: Boolean) {
        getPrefs(context).edit().putBoolean(KEY_BREATHING_ENABLED, enabled).apply()
    }
    
    // Epilepsy
    fun isEpilepsyEnabled(context: Context): Boolean {
        return getPrefs(context).getBoolean(KEY_EPILEPSY_ENABLED, false)
    }
    
    fun setEpilepsyEnabled(context: Context, enabled: Boolean) {
        getPrefs(context).edit().putBoolean(KEY_EPILEPSY_ENABLED, enabled).apply()
    }
    
    // Animation
    fun isAnimationEnabled(context: Context): Boolean {
        return getPrefs(context).getBoolean(KEY_ANIMATION_ENABLED, false)
    }
    
    fun setAnimationEnabled(context: Context, enabled: Boolean) {
        getPrefs(context).edit().putBoolean(KEY_ANIMATION_ENABLED, enabled).apply()
    }
    
    // Torch
    fun isTorchEnabled(context: Context): Boolean {
        return getPrefs(context).getBoolean(KEY_TORCH_ENABLED, false)
    }
    
    fun setTorchEnabled(context: Context, enabled: Boolean) {
        getPrefs(context).edit().putBoolean(KEY_TORCH_ENABLED, enabled).apply()
    }
}
