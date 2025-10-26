package com.f14my.glyphalw.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.f14my.glyphalw.GlyphManager
import com.f14my.glyphalw.R
import com.f14my.glyphalw.ui.theme.NDot57
import com.f14my.glyphalw.ui.theme.NType82
import com.f14my.glyphalw.ui.theme.Space
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
        mutableStateMapOf<GlyphManager.Light, GlyphState>().apply {
            GlyphManager.Light.values().forEach {
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
        Text(
            text = stringResource(id = R.string.glyph_control_desc),
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W400),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(24.dp))

        GlyphManager.Light.values().forEach { light ->
            val state = glyphStates.getValue(light)
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
                            .heightIn(min = 56.dp)
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = light.name,
                            style = MaterialTheme.typography.titleLarge.copy(fontFamily = NType82, fontWeight = FontWeight.W500),
                            modifier = Modifier.weight(1f)
                        )
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
                    AnimatedVisibility(visible = state.isEnabled) {
                        Column(modifier = Modifier.padding(top = 4.dp, bottom = 12.dp)) {
                            Text(
                                text = stringResource(id = R.string.bringes),
                                style = MaterialTheme.typography.titleSmall,
                                modifier = Modifier.padding(horizontal = 24.dp)
                            )
                            Slider(
                                value = state.brightness,
                                onValueChange = { newBrightness ->
                                    glyphStates[light] = state.copy(brightness = newBrightness)
                                    scope.launch {
                                        val brightnessInt = (newBrightness * GlyphManager.MaxBrightness).roundToInt()
                                        GlyphManager.setLightBrightness(light, brightnessInt)
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 24.dp)
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp)) // Add space between cards
        }
        Spacer(modifier = Modifier.height(16.dp)) // Adjust spacing before the button
        OutlinedButton(
            onClick = {
                scope.launch {
                    GlyphManager.turnAllOff()
                }
                GlyphManager.Light.values().forEach {
                    glyphStates[it] = GlyphState()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Сбросить",
                style = MaterialTheme.typography.bodyMedium.copy(fontFamily = Space, fontWeight = FontWeight.Bold))
        }
    }
}