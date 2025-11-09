package com.f14my.myglyph.features

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import com.f14my.myglyph.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.f14my.myglyph.GlyphManager
import com.f14my.myglyph.ui.theme.NType82
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlin.math.roundToInt

@Composable
fun EpilepsyCard() {
    var isBlinkingEnabled by remember { mutableStateOf(false) }
    var blinkIntensity by remember { mutableFloatStateOf(0.5f) }
    var blinkBrightness by remember { mutableFloatStateOf(1.0f) }

    if (isBlinkingEnabled) {
        LaunchedEffect(blinkIntensity, blinkBrightness) {
            val delayMillis = (500 - (blinkIntensity * 450)).toLong()
            val brightnessInt = (blinkBrightness * GlyphManager.MaxBrightness).roundToInt()

            while (isActive) {
                GlyphManager.Glyph.entries.forEach { light ->
                    GlyphManager.setLightBrightness(light, brightnessInt)
                }
                delay(delayMillis)
                GlyphManager.turnAllOff()
                delay(delayMillis)
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
            containerColor = if (isBlinkingEnabled) 
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
                        text = stringResource(id = R.string.epilepsy),
                        style = MaterialTheme.typography.titleMedium.copy(fontFamily = NType82, fontWeight = FontWeight.W500)
                    )
                    Text(
                        text = stringResource(id = R.string.epilepsy_desc),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Switch(
                    checked = isBlinkingEnabled,
                    onCheckedChange = { isBlinkingEnabled = it }
                )
            }
            AnimatedVisibility(visible = isBlinkingEnabled) {
                Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)) {
                    Text(
                        text = stringResource(id = R.string.speed),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Slider(
                        value = blinkIntensity,
                        onValueChange = { blinkIntensity = it },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = stringResource(id = R.string.bringes),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    Slider(
                        value = blinkBrightness,
                        onValueChange = { blinkBrightness = it },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}