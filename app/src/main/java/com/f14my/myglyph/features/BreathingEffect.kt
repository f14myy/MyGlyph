package com.f14my.myglyph.features

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.f14my.myglyph.GlyphManager
import com.f14my.myglyph.R
import com.f14my.myglyph.ui.theme.NType82
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlin.math.roundToInt
import kotlin.math.sin

@Composable
fun BreathingEffectCard() {
    var isEnabled by remember { mutableStateOf(false) }
    var speed by remember { mutableFloatStateOf(0.5f) }
    var maxBrightness by remember { mutableFloatStateOf(1.0f) }

    if (isEnabled) {
        LaunchedEffect(speed, maxBrightness) {
            val cycleDuration = (3000 - (speed * 2500)).toLong()
            val steps = 50
            val stepDelay = cycleDuration / steps
            val maxBrightnessInt = (maxBrightness * GlyphManager.MaxBrightness).roundToInt()

            while (isActive) {
                for (step in 0 until steps) {
                    if (!isActive) break
                    val progress = step.toFloat() / steps
                    val sineValue = sin(progress * Math.PI).toFloat()
                    val currentBrightness = (maxBrightnessInt * sineValue).roundToInt()
                    
                    GlyphManager.Glyph.values().forEach { glyph ->
                        GlyphManager.setLightBrightness(glyph, currentBrightness)
                    }
                    delay(stepDelay)
                }
            }
        }
    } else {
        LaunchedEffect(Unit) {
            GlyphManager.turnAllOff()
        }
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
                        text = "Breathing",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontFamily = NType82, 
                            fontWeight = FontWeight.W500
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Smooth breathing effect for all glyphs",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Switch(
                    checked = isEnabled,
                    onCheckedChange = { isEnabled = it }
                )
            }
            AnimatedVisibility(visible = isEnabled) {
                Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)) {
                    Text(
                        text = stringResource(id = R.string.speed),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
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
                        value = maxBrightness,
                        onValueChange = { maxBrightness = it },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}
