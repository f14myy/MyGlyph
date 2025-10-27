package com.f14my.glyphalw

import com.topjohnwu.superuser.Shell
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object GlyphManager {

    const val MaxBrightness = 4095 // Делаем public, чтобы использовать в MainActivity
    private const val PathRoot = "sys/devices/platform/soc/984000.i2c/i2c-0/0-0020/leds/aw210xx_led" // <-- ПРАВИЛЬНО

    enum class Light(val path: String) {
        Camera("$PathRoot/rear_cam_led_br"),
        Diagonal("$PathRoot/front_cam_led_br"),
        Main("$PathRoot/round_leds_br"),
        Line("$PathRoot/vline_leds_br"),
        Dot("$PathRoot/dot_led_br")
    }

    /**
     * НАША ГЛАВНАЯ ФУНКЦИЯ
     * Устанавливает яркость для одного глифа.
     * @param brightness от 0 (выкл) до 4095 (макс).
     */
    suspend fun setLightBrightness(light: Light, brightness: Int) {
        // Убедимся, что яркость в нужных пределах
        val safeBrightness = brightness.coerceIn(0, MaxBrightness)

        val command = "echo $safeBrightness > ${light.path}"
        withContext(Dispatchers.IO) {
            Shell.cmd(command).exec()
            // Можно добавить проверку на !result.isSuccess
        }
    }

    /**
     * Выключает все глифы.
     */
    suspend fun turnAllOff() {
        for (light in Light.values()) {
            setLightBrightness(light, 0)
        }
    }
    suspend fun turnAllOn() {
        for (light in Light.values()) {
            setLightBrightness(light, 4095)
        }
    }
}