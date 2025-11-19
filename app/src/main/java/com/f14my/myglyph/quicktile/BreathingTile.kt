package com.f14my.myglyph.quicktile

import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import com.f14my.myglyph.GlyphManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * Тайл для включения/выключения эффекта Breathing
 */
class BreathingTile : TileService() {
    
    private var job: Job? = null
    
    override fun onStartListening() {
        super.onStartListening()
        updateTileState()
    }
    
    override fun onClick() {
        super.onClick()
        
        val isEnabled = GlyphTilePreferences.isBreathingEnabled(applicationContext)
        val newState = !isEnabled
        
        GlyphTilePreferences.setBreathingEnabled(applicationContext, newState)
        
        if (newState) {
            startBreathing()
        } else {
            stopBreathing()
        }
        
        updateTileState()
    }
    
    private fun updateTileState() {
        val isEnabled = GlyphTilePreferences.isBreathingEnabled(applicationContext)
        qsTile?.apply {
            state = if (isEnabled) Tile.STATE_ACTIVE else Tile.STATE_INACTIVE
            label = "Breathing"
            updateTile()
        }
    }
    
    private fun startBreathing() {
        job?.cancel()
        job = CoroutineScope(Dispatchers.IO).launch {
            // Простая реализация breathing эффекта
            val cycleDuration = 2000L
            val steps = 50
            val stepDelay = cycleDuration / steps
            
            while (GlyphTilePreferences.isBreathingEnabled(applicationContext)) {
                for (step in 0 until steps) {
                    if (!GlyphTilePreferences.isBreathingEnabled(applicationContext)) break
                    
                    val progress = step.toFloat() / steps
                    val sineValue = kotlin.math.sin(progress * Math.PI).toFloat()
                    val currentBrightness = (GlyphManager.MaxBrightness * sineValue).toInt()
                    
                    GlyphManager.Glyph.entries.forEach { glyph ->
                        GlyphManager.setLightBrightness(glyph, currentBrightness)
                    }
                    kotlinx.coroutines.delay(stepDelay)
                }
            }
        }
    }
    
    private fun stopBreathing() {
        job?.cancel()
        CoroutineScope(Dispatchers.IO).launch {
            GlyphManager.turnAllOff()
        }
    }
    
    override fun onStopListening() {
        super.onStopListening()
        if (!GlyphTilePreferences.isBreathingEnabled(applicationContext)) {
            stopBreathing()
        }
    }
}
