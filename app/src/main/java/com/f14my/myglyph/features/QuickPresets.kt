package com.f14my.myglyph.features

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.f14my.myglyph.GlyphManager
import com.f14my.myglyph.ui.theme.NType82
import kotlinx.coroutines.launch

data class BrightnessPreset(
    val name: String,
    val brightness: Float,
    val description: String
)

@Composable
fun QuickPresetsCard() {
    val scope = rememberCoroutineScope()
    
    val presets = listOf(
        BrightnessPreset("Low", 0.25f, "25%"),
        BrightnessPreset("Medium", 0.5f, "50%"),
        BrightnessPreset("High", 0.75f, "75%"),
        BrightnessPreset("Max", 1.0f, "100%")
    )
    
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Quick Presets",
            style = MaterialTheme.typography.titleMedium.copy(
                fontFamily = NType82,
                fontWeight = FontWeight.W500
            )
        )
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            presets.forEach { preset ->
                PresetButton(
                    preset = preset,
                    modifier = Modifier.weight(1f),
                    onClick = {
                        scope.launch {
                            val brightnessInt = (preset.brightness * GlyphManager.MaxBrightness).toInt()
                            GlyphManager.Glyph.values().forEach { glyph ->
                                GlyphManager.setLightBrightness(glyph, brightnessInt)
                            }
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun PresetButton(
    preset: BrightnessPreset,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.height(56.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = preset.description,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontFamily = NType82,
                    fontWeight = FontWeight.W500
                )
            )
        }
    }
}
