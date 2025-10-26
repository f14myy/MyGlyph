package com.f14my.glyphalw.features

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.f14my.glyphalw.GlyphManager
import com.f14my.glyphalw.ui.theme.NType82
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlin.math.roundToInt

enum class AnimationEffect(val displayName: String) {
    SMOOTH("Плавное переливание"),
    GLITCH("Глитч-эффект")
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
                        GlyphManager.Light.Line,
                        GlyphManager.Light.Main,
                        GlyphManager.Light.Diagonal,
                        GlyphManager.Light.Dot,
                        GlyphManager.Light.Camera
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
                    val glyphs = GlyphManager.Light.values().toList()
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
            }
        }
    } else {
        LaunchedEffect(Unit) { GlyphManager.turnAllOff() }
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
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
                        text = "Анимации",
                        style = MaterialTheme.typography.titleLarge.copy(fontFamily = NType82, fontWeight = FontWeight.W500),
                    )
                    Text(
                        text = "Включает и настраивает эффекты глифов",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Switch(checked = isEnabled, onCheckedChange = { isEnabled = it })
            }

            AnimatedVisibility(visible = isEnabled) {
                Column(modifier = Modifier.padding(bottom = 12.dp)) {
                    ExposedDropdownMenuBox(
                        expanded = isDropdownExpanded,
                        onExpandedChange = { isDropdownExpanded = it },
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        TextField(
                            value = selectedEffect.displayName,
                            onValueChange = {},
                            readOnly = true,
                            textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSurface),
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isDropdownExpanded) },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                disabledContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                            ),
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

                    val speedLabel = if (selectedEffect == AnimationEffect.GLITCH) "Частота" else "Скорость"

                    Text(speedLabel, style = MaterialTheme.typography.titleSmall, modifier = Modifier.padding(horizontal = 24.dp, vertical = 4.dp))
                    Slider(value = speed, onValueChange = { speed = it }, modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp))

                    Text("Яркость", style = MaterialTheme.typography.titleSmall, modifier = Modifier.padding(horizontal = 24.dp, vertical = 4.dp))
                    Slider(value = brightness, onValueChange = { brightness = it }, modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp))
                }
            }
        }
    }
}