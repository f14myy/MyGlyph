package com.f14my.myglyph.features

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.f14my.myglyph.GlyphManager
import com.f14my.myglyph.ui.theme.NType82
import com.f14my.myglyph.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlin.math.roundToInt

enum class AnimationEffect(val displayName: String) {
    SMOOTH("SMOOTH"),
    GLITCH("GLITCH"),
    PULSE("PULSE"),
    RIPPLE("RIPPLE"),
    SPARKLE("SPARKLE"),
    TWINKLE("TWINKLE")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimationCard() {
    var isEnabled by remember { mutableStateOf(false) }
    var speed by remember { mutableStateOf(0.5f) }
    var brightness by remember { mutableStateOf(1.0f) }
    var selectedEffect by remember { mutableStateOf(AnimationEffect.SMOOTH) }
    var isDropdownExpanded by remember { mutableStateOf(false) }

    if (isEnabled) {
        LaunchedEffect(isEnabled, speed, brightness, selectedEffect) {
            val brightnessInt = (brightness * GlyphManager.MaxBrightness).roundToInt()
            when (selectedEffect) {
                AnimationEffect.SMOOTH -> {
                    val glyphOrder = listOf(
                        GlyphManager.Glyph.Camera,
                        GlyphManager.Glyph.Diagonal,
                        GlyphManager.Glyph.Main,
                        GlyphManager.Glyph.Line,
                        GlyphManager.Glyph.Dot
                    )
                    val cycleDuration = (1000 - (speed * 900)).toLong()
                    val fadeDuration = cycleDuration / 3
                    val holdDuration = cycleDuration / 3
                    val fadeSteps = 15
                    val fadeStepDelay = if (fadeSteps > 0) fadeDuration / fadeSteps else 0
                    var currentIndex = 0
                    while (isActive) {
                        val currentLight = glyphOrder[currentIndex]
                        if (fadeStepDelay > 0) {
                            for (i in 1..fadeSteps) {
                                if (!isActive) break
                                GlyphManager.setLightBrightness(currentLight, (brightnessInt * (i.toFloat() / fadeSteps)).roundToInt())
                                delay(fadeStepDelay)
                            }
                        }
                        GlyphManager.setLightBrightness(currentLight, brightnessInt)
                        delay(holdDuration)
                        if (fadeStepDelay > 0) {
                            for (i in (fadeSteps - 1) downTo 0) {
                                if (!isActive) break
                                GlyphManager.setLightBrightness(currentLight, (brightnessInt * (i.toFloat() / fadeSteps)).roundToInt())
                                delay(fadeStepDelay)
                            }
                        }
                        if(isActive) GlyphManager.setLightBrightness(currentLight, 0)
                        currentIndex = (currentIndex + 1) % glyphOrder.size
                    }
                }
                AnimationEffect.GLITCH -> {
                    val glyphs = GlyphManager.Glyph.values().toList()
                    val baseDelay = (150 - (speed * 130)).toLong().coerceAtLeast(10)
                    while (isActive) {
                        val randomGlyph = glyphs.random()
                        val glitchDuration = (10..50).random().toLong()
                        GlyphManager.setLightBrightness(randomGlyph, brightnessInt)
                        delay(glitchDuration)
                        GlyphManager.setLightBrightness(randomGlyph, 0)
                        delay(baseDelay + (0..30).random())
                    }
                }
                AnimationEffect.PULSE -> {
                    val allGlyphs = GlyphManager.Glyph.values()
                    val cycleDuration = (1500 - (speed * 1300)).toLong()
                    val fadeDuration = cycleDuration / 2
                    val fadeSteps = 15
                    val fadeStepDelay = if (fadeSteps > 0) fadeDuration / fadeSteps else 0

                    while (isActive) {
                        // Fade in
                        if (fadeStepDelay > 0) {
                            for (i in 1..fadeSteps) {
                                if (!isActive) break
                                val currentBrightness = (brightnessInt * (i.toFloat() / fadeSteps)).roundToInt()
                                allGlyphs.forEach { GlyphManager.setLightBrightness(it, currentBrightness) }
                                delay(fadeStepDelay)
                            }
                        }
                        allGlyphs.forEach { GlyphManager.setLightBrightness(it, brightnessInt) }
                        delay(200) // Hold at max brightness

                        // Fade out
                        if (fadeStepDelay > 0) {
                            for (i in (fadeSteps - 1) downTo 0) {
                                if (!isActive) break
                                val currentBrightness = (brightnessInt * (i.toFloat() / fadeSteps)).roundToInt()
                                allGlyphs.forEach { GlyphManager.setLightBrightness(it, currentBrightness) }
                                delay(fadeStepDelay)
                            }
                        }
                        if(isActive) allGlyphs.forEach { GlyphManager.setLightBrightness(it, 0) }
                        delay(200) // Pause before next pulse
                    }
                }
                AnimationEffect.RIPPLE -> {
                    val rippleOrder = listOf(
                        listOf(GlyphManager.Glyph.Main),
                        listOf(GlyphManager.Glyph.Diagonal, GlyphManager.Glyph.Line),
                        listOf(GlyphManager.Glyph.Camera, GlyphManager.Glyph.Dot)
                    )
                    val rippleDelay = (300 - (speed * 250)).toLong().coerceAtLeast(50)
                    while (isActive) {
                        for (group in rippleOrder) {
                            if (!isActive) break
                            group.forEach { GlyphManager.setLightBrightness(it, brightnessInt) }
                            delay(rippleDelay)
                            group.forEach { GlyphManager.setLightBrightness(it, 0) }
                        }
                        delay(rippleDelay * 2)
                    }
                }
                AnimationEffect.SPARKLE -> {
                    val allGlyphs = GlyphManager.Glyph.values().toList()
                    val baseDelay = (200 - (speed * 180)).toLong().coerceAtLeast(20)
                    while (isActive) {
                        val glyphToSparkle = allGlyphs.random()
                        val sparkleBrightness = (brightness * GlyphManager.MaxBrightness * (0.5f + Math.random() * 0.5f)).roundToInt()
                        val sparkleDuration = (20..80).random().toLong()

                        GlyphManager.setLightBrightness(glyphToSparkle, sparkleBrightness)
                        delay(sparkleDuration)
                        GlyphManager.setLightBrightness(glyphToSparkle, 0)
                        delay(baseDelay)
                    }
                }
                AnimationEffect.TWINKLE -> {
                    val allGlyphs = GlyphManager.Glyph.values().toList()
                    val baseDelay = (300 - (speed * 280)).toLong().coerceAtLeast(20)
                    val twinkleDuration = (400 - (speed * 350)).toLong().coerceAtLeast(50)
                    val fadeSteps = 10
                    val fadeStepDelay = if (fadeSteps > 0) twinkleDuration / (fadeSteps * 2) else 0

                    while (isActive) {
                        val glyphToTwinkle = allGlyphs.random()
                        val maxTwinkleBrightness = (brightnessInt * (0.5f + Math.random() * 0.5f)).roundToInt()

                        if (fadeStepDelay > 0) {
                            // Fade in
                            for (i in 1..fadeSteps) {
                                if (!isActive) break
                                val currentBrightness = (maxTwinkleBrightness * (i.toFloat() / fadeSteps)).roundToInt()
                                GlyphManager.setLightBrightness(glyphToTwinkle, currentBrightness)
                                delay(fadeStepDelay)
                            }
                            // Fade out
                            for (i in (fadeSteps - 1) downTo 0) {
                                if (!isActive) break
                                val currentBrightness = (maxTwinkleBrightness * (i.toFloat() / fadeSteps)).roundToInt()
                                GlyphManager.setLightBrightness(glyphToTwinkle, currentBrightness)
                                delay(fadeStepDelay)
                            }
                        }

                        if (isActive) {
                            GlyphManager.setLightBrightness(glyphToTwinkle, 0)
                            delay(baseDelay)
                        }
                    }
                }
            }
        }
    } else {
        LaunchedEffect(Unit) { GlyphManager.turnAllOff() }
    }

    OutlinedCard(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.outlinedCardColors(
            containerColor = if (isEnabled) 
                MaterialTheme.colorScheme.surfaceVariant 
            else 
                MaterialTheme.colorScheme.surface
        )
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(id = R.string.animation),
                        style = MaterialTheme.typography.titleMedium.copy(fontFamily = NType82, fontWeight = FontWeight.W500)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = stringResource(id = R.string.animation_desc),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Switch(checked = isEnabled, onCheckedChange = { isEnabled = it })
            }

            AnimatedVisibility(visible = isEnabled) {
                Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)) {
                    ExposedDropdownMenuBox(
                        expanded = isDropdownExpanded,
                        onExpandedChange = { isDropdownExpanded = it },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = selectedEffect.displayName,
                            onValueChange = {},
                            readOnly = true,
                            textStyle = MaterialTheme.typography.bodyMedium,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isDropdownExpanded) },
                            modifier = Modifier.menuAnchor().fillMaxWidth()
                        )
                        ExposedDropdownMenu(
                            expanded = isDropdownExpanded,
                            onDismissRequest = { isDropdownExpanded = false }
                        ) {
                            AnimationEffect.values().forEach { effect ->
                                DropdownMenuItem(
                                    text = { Text(effect.displayName) },
                                    onClick = {
                                        selectedEffect = effect
                                        isDropdownExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    Text(
                        text = stringResource(id = R.string.speed),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    Slider(
                        value = speed,
                        onValueChange = { speed = it },
                        modifier = Modifier.fillMaxWidth()
                    )
                    
                    Text(
                        text = stringResource(id = R.string.bringes),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    Slider(
                        value = brightness,
                        onValueChange = { brightness = it },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}
