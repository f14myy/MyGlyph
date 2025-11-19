package com.f14my.myglyph.quicktile

import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import com.f14my.myglyph.GlyphManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * Тайл для включения/выключения эффекта Epilepsy
 */
class EpilepsyTile : TileService() {
    
    private var job: Job? = null
    
    override fun onStartListening() {
        super.onStartListening()
        updateTileState()
    }
    
    override fun onClick() {
        super.onClick()
        
        val isEnabled = GlyphTilePreferences.isEpilepsyEnabled(applicationContext)
        val newState = !isEnabled
        
        GlyphTilePreferences.setEpilepsyEnabled(applicationContext, newState)
        
        if (newState) {
            startEpilepsy()
        } else {
            stopEpilepsy()
        }
        
        updateTileState()
    }
    
    private fun updateTileState() {
        val isEnabled = GlyphTilePreferences.isEpilepsyEnabled(applicationContext)
        qsTile?.apply {
            state = if (isEnabled) Tile.STATE_ACTIVE else Tile.STATE_INACTIVE
            label = "Epilepsy"
            updateTile()
        }
    }
    
    private fun startEpilepsy() {
        job?.cancel()
        job = CoroutineScope(Dispatchers.IO).launch {
            // Простая реализация epilepsy эффекта
            val delayMillis = 200L
            val brightnessInt = GlyphManager.MaxBrightness
            
            while (GlyphTilePreferences.isEpilepsyEnabled(applicationContext)) {
                GlyphManager.Glyph.entries.forEach { light ->
                    GlyphManager.setLightBrightness(light, brightnessInt)
                }
                kotlinx.coroutines.delay(delayMillis)
                GlyphManager.turnAllOff()
                kotlinx.coroutines.delay(delayMillis)
            }
        }
    }
    
    private fun stopEpilepsy() {
        job?.cancel()
        CoroutineScope(Dispatchers.IO).launch {
            GlyphManager.turnAllOff()
        }
    }
    
    override fun onStopListening() {
        super.onStopListening()
        if (!GlyphTilePreferences.isEpilepsyEnabled(applicationContext)) {
            stopEpilepsy()
        }
    }
}
