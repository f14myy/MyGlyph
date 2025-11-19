package com.f14my.myglyph.quicktile

import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import com.f14my.myglyph.GlyphManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Тайл для включения/выключения всех глифов (фонарик)
 */
class TorchTile : TileService() {
    
    override fun onStartListening() {
        super.onStartListening()
        updateTileState()
    }
    
    override fun onClick() {
        super.onClick()
        
        val isEnabled = GlyphTilePreferences.isTorchEnabled(applicationContext)
        val newState = !isEnabled
        
        GlyphTilePreferences.setTorchEnabled(applicationContext, newState)
        
        CoroutineScope(Dispatchers.IO).launch {
            if (newState) {
                GlyphManager.turnAllOn()
            } else {
                GlyphManager.turnAllOff()
            }
        }
        
        updateTileState()
    }
    
    private fun updateTileState() {
        val isEnabled = GlyphTilePreferences.isTorchEnabled(applicationContext)
        qsTile?.apply {
            state = if (isEnabled) Tile.STATE_ACTIVE else Tile.STATE_INACTIVE
            label = "Glyph Torch"
            updateTile()
        }
    }
    
    override fun onStopListening() {
        super.onStopListening()
        if (!GlyphTilePreferences.isTorchEnabled(applicationContext)) {
            CoroutineScope(Dispatchers.IO).launch {
                GlyphManager.turnAllOff()
            }
        }
    }
}
