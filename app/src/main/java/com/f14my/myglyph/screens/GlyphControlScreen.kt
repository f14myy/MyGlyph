package com.f14my.myglyph.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.f14my.myglyph.GlyphManager
import com.f14my.myglyph.R
import com.f14my.myglyph.features.QuickPresetsCard
import com.f14my.myglyph.ui.theme.NDot57
import com.f14my.myglyph.ui.theme.NType82
import com.f14my.myglyph.ui.theme.Space
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


data class GlyphState(
    val isEnabled: Boolean = false,
    val brightness: Float = 1.0f
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GlyphControlScreen() {
    val scope = rememberCoroutineScope()

    val glyphStates = remember {
        mutableStateMapOf<GlyphManager.Glyph, GlyphState>().apply {
            GlyphManager.Glyph.values().forEach {
                put(it, GlyphState())
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.glyph_control),
            style = MaterialTheme.typography.headlineLarge.copy(fontFamily = NDot57, fontWeight = FontWeight.W500)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(id = R.string.glyph_control_desc),
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W400),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(20.dp))
        
        QuickPresetsCard()
        
        Spacer(modifier = Modifier.height(24.dp))

        GlyphManager.Glyph.values().forEach { light ->
            val state = glyphStates.getValue(light)
            
            OutlinedCard(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.outlinedCardColors(
                    containerColor = if (state.isEnabled) 
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
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = light.name,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontFamily = NType82, 
                                    fontWeight = FontWeight.W500
                                )
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            if (state.isEnabled) {
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "${(state.brightness * 100).roundToInt()}%",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                        
                        Switch(
                            checked = state.isEnabled,
                            onCheckedChange = { isChecked ->
                                glyphStates[light] = state.copy(isEnabled = isChecked)
                                scope.launch {
                                    if (isChecked) {
                                        val brightnessInt = (state.brightness * GlyphManager.MaxBrightness).roundToInt()
                                        GlyphManager.setLightBrightness(light, brightnessInt)
                                    } else {
                                        GlyphManager.setLightBrightness(light, 0)
                                    }
                                }
                            }
                        )
                    }
                    
                    AnimatedVisibility(
                        visible = state.isEnabled,
                        enter = expandVertically() + fadeIn(),
                        exit = shrinkVertically() + fadeOut()
                    ) {
                        Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = stringResource(id = R.string.bringes),
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = "${(state.brightness * GlyphManager.MaxBrightness).roundToInt()}",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Slider(
                                value = state.brightness,
                                onValueChange = { newBrightness ->
                                    glyphStates[light] = state.copy(brightness = newBrightness)
                                    scope.launch {
                                        val brightnessInt = (newBrightness * GlyphManager.MaxBrightness).roundToInt()
                                        GlyphManager.setLightBrightness(light, brightnessInt)
                                    }
                                },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(
                onClick = {
                    scope.launch {
                        GlyphManager.turnAllOn()
                    }
                    GlyphManager.Glyph.values().forEach {
                        glyphStates[it] = GlyphState(isEnabled = true, brightness = 1.0f)
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "All On",
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontFamily = NType82, 
                        fontWeight = FontWeight.W500
                    ),
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            
            OutlinedButton(
                onClick = {
                    scope.launch {
                        GlyphManager.turnAllOff()
                    }
                    GlyphManager.Glyph.values().forEach {
                        glyphStates[it] = GlyphState()
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = stringResource(id = R.string.reset),
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontFamily = NType82, 
                        fontWeight = FontWeight.W500
                    ),
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}