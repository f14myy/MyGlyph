package com.f14my.glyphalw

import com.topjohnwu.superuser.Shell
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object GlyphManager {

    const val MaxBrightness = 4095
    private const val PathRoot = "/sys/devices/platform/soc/984000.i2c/i2c-0/0-0020/leds/aw210xx_led" // <-- ПРАВИЛЬНО

    enum class Glyph(val path: String) {
        Camera("$PathRoot/rear_cam_led_br"),
        Diagonal("$PathRoot/front_cam_led_br"),
        Main("$PathRoot/round_leds_br"),
        Line("$PathRoot/vline_leds_br"),
        Dot("$PathRoot/dot_led_br")
    }
    
    suspend fun setLightBrightness(glyph: Glyph, brightness: Int) {
        val safeBrightness = brightness.coerceIn(0, MaxBrightness)

        val command = "echo $safeBrightness > ${glyph.path}"
        withContext(Dispatchers.IO) {
            Shell.cmd(command).exec()
        }
    }
    
    suspend fun turnAllOff() {
        for (glyph in Glyph.values()) {
            setLightBrightness(glyph, 0)
        }
    }
    suspend fun turnAllOn() {
        for (glyph in Glyph.values()) {
            setLightBrightness(glyph, 4095)
        }
    }
}