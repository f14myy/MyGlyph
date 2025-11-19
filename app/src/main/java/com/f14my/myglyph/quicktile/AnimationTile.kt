package com.f14my.myglyph.quicktile

import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import com.f14my.myglyph.GlyphManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * Тайл для включения/выключения эффекта Animation (SMOOTH)
 */
class AnimationTile : TileService() {
    
    private var job: Job? = null
    
    override fun onStartListening() {
        super.onStartListening()
        updateTileState()
    }
    
    override fun onClick() {
        super.onClick()
        
        val isEnabled = GlyphTilePreferences.isAnimationEnabled(applicationContext)
        val newState = !isEnabled
        
        GlyphTilePreferences.setAnimationEnabled(applicationContext, newState)
        
        if (newState) {
            startAnimation()
        } else {
            stopAnimation()
        }
        
        updateTileState()
    }
    
    private fun updateTileState() {
        val isEnabled = GlyphTilePreferences.isAnimationEnabled(applicationContext)
        qsTile?.apply {
            state = if (isEnabled) Tile.STATE_ACTIVE else Tile.STATE_INACTIVE
            label = "Animation"
            updateTile()
        }
    }
    
    private fun startAnimation() {
        job?.cancel()
        job = CoroutineScope(Dispatchers.IO).launch {
            // Простая реализация SMOOTH анимации
            val glyphOrder = listOf(
                GlyphManager.Glyph.Camera,
                GlyphManager.Glyph.Diagonal,
                GlyphManager.Glyph.Main,
                GlyphManager.Glyph.Line,
                GlyphManager.Glyph.Dot
            )
            val cycleDuration = 500L
            val fadeDuration = cycleDuration / 3
            val holdDuration = cycleDuration / 3
            val fadeSteps = 15
            val fadeStepDelay = if (fadeSteps > 0) fadeDuration / fadeSteps else 0
            val brightnessInt = GlyphManager.MaxBrightness
            var currentIndex = 0
            
            while (GlyphTilePreferences.isAnimationEnabled(applicationContext)) {
                val currentLight = glyphOrder[currentIndex]
                
                // Fade in
                if (fadeStepDelay > 0) {
                    for (i in 1..fadeSteps) {
                        if (!GlyphTilePreferences.isAnimationEnabled(applicationContext)) break
                        GlyphManager.setLightBrightness(currentLight, (brightnessInt * (i.toFloat() / fadeSteps)).toInt())
                        kotlinx.coroutines.delay(fadeStepDelay)
                    }
                }
                
                GlyphManager.setLightBrightness(currentLight, brightnessInt)
                kotlinx.coroutines.delay(holdDuration)
                
                // Fade out
                if (fadeStepDelay > 0) {
                    for (i in (fadeSteps - 1) downTo 0) {
                        if (!GlyphTilePreferences.isAnimationEnabled(applicationContext)) break
                        GlyphManager.setLightBrightness(currentLight, (brightnessInt * (i.toFloat() / fadeSteps)).toInt())
                        kotlinx.coroutines.delay(fadeStepDelay)
                    }
                }
                
                if (GlyphTilePreferences.isAnimationEnabled(applicationContext)) {
                    GlyphManager.setLightBrightness(currentLight, 0)
                }
                
                currentIndex = (currentIndex + 1) % glyphOrder.size
            }
        }
    }
    
    private fun stopAnimation() {
        job?.cancel()
        CoroutineScope(Dispatchers.IO).launch {
            GlyphManager.turnAllOff()
        }
    }
    
    override fun onStopListening() {
        super.onStopListening()
        if (!GlyphTilePreferences.isAnimationEnabled(applicationContext)) {
            stopAnimation()
        }
    }
}
