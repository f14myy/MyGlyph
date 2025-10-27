package com.f14my.glyphalw.features

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
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
import com.f14my.glyphalw.GlyphManager
import com.f14my.glyphalw.R
import com.f14my.glyphalw.ui.theme.NType82
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlin.math.roundToInt

@Composable
fun EpilepsyCard() {
    var isBlinkingEnabled by remember { mutableStateOf(false) }
    var blinkIntensity by remember { mutableFloatStateOf(0.5f) }
    var blinkBrightness by remember { mutableFloatStateOf(1.0f) }

    if (isBlinkingEnabled) {
        LaunchedEffect(isBlinkingEnabled, blinkIntensity, blinkBrightness) {
            val delayMillis = (500 - (blinkIntensity * 450)).toLong()
            val brightnessInt = (blinkBrightness * GlyphManager.MaxBrightness).roundToInt()

            while (isActive) {
                GlyphManager.Glyph.entries.forEach { glyph ->
                    GlyphManager.setLightBrightness(glyph, brightnessInt)
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

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(id = R.string.epilepsy),
                        style = MaterialTheme.typography.titleLarge.copy(fontFamily = NType82, fontWeight = FontWeight.W500),
                    )
                    Text(
                        text = stringResource(id = R.string.epilepsy_desc),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Switch(
                    checked = isBlinkingEnabled,
                    onCheckedChange = { isBlinkingEnabled = it }
                )
            }
            AnimatedVisibility(visible = isBlinkingEnabled) {
                Column(modifier = Modifier.padding(bottom = 12.dp)) {
                    Text(
                        text = stringResource(id = R.string.intensity),
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 4.dp)
                    )
                    Slider(
                        value = blinkIntensity,
                        onValueChange = { blinkIntensity = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                    )
                    Text(
                        text = stringResource(id = R.string.bringes),
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 4.dp)
                    )
                    Slider(
                        value = blinkBrightness,
                        onValueChange = { blinkBrightness = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                    )
                }
            }
        }
    }
}
