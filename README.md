# MyGlyph

<div align="center">

**Advanced Glyph Interface Control for Nothing Phone**

[![Version](https://img.shields.io/badge/version-1.2-red.svg)](https://github.com/f14myy/MyGlyph/releases)
[![Android](https://img.shields.io/badge/Android-12%2B-green.svg)](https://www.android.com)
[![Root Required](https://img.shields.io/badge/Root-Required-orange.svg)](https://topjohnwu.github.io/Magisk/)

</div>

---

## 📱 Overview

MyGlyph is a powerful application that provides complete control over the Glyph Interface on Nothing Phone devices. With a redesigned Nothing OS-inspired interface, Quick Settings integration, and advanced customization options, MyGlyph takes your Glyph experience to the next level.

## ✨ Features

### 🎨 Nothing OS Design
- **Monochrome Aesthetic**: Clean black and white interface with signature red accents
- **Nothing Typography**: Authentic NDot57 matrix font for headers and NType82 for body text
- **Dark & Light Themes**: Full theme support with system, light, and dark modes
- **Optimized Spacing**: Carefully crafted UI with improved readability

### ⚡ Quick Settings Tiles
Access your favorite Glyph functions directly from the notification shade:
- **Breathing Tile** - Smooth breathing effect toggle
- **Epilepsy Tile** - Rapid flash effect toggle
- **Animation Tile** - Quick access to smooth animations
- **Torch Tile** - Turn all glyphs on as a flashlight

### 🎛️ Glyph Control
- **Individual Control**: Adjust each glyph independently
- **Brightness Presets**: One-tap access to 25%, 50%, 75%, and 100% brightness
- **Real-time Display**: See brightness values from 0-4095 in real-time
- **Quick Actions**: All On / All Off buttons for instant control

### 🌟 Glyph Tools
- **Breathing Effect**: Smooth pulsing animation with adjustable speed and brightness
- **Epilepsy Mode**: Rapid flashing effect (use with caution)
- **Animations**: Multiple animation patterns including smooth, glitch, pulse, ripple, sparkle, and twinkle

## 📋 Requirements

- **Device**: Nothing Phone (1)
- **Android**: 12 (API 31) or higher
- **Root Access**: Required for system-level glyph control
- **Storage**: ~10 MB

## 🚀 Installation

1. Download the latest APK from [Releases](https://github.com/f14myy/MyGlyph/releases)
2. Enable "Install from Unknown Sources" in your device settings
3. Install the APK
4. Grant root permissions when prompted
5. Enjoy full control of your Glyphs!

## 🎯 Usage

### Quick Settings Tiles
1. Swipe down to open the notification shade
2. Tap the edit button (pencil icon)
3. Find MyGlyph tiles: Breathing, Epilepsy, Animation, Glyph Torch
4. Drag them to your active tiles area
5. Tap any tile to toggle the effect on/off

### Theme Switching
1. Navigate to the **About** tab
2. Find the "Theme" dropdown menu
3. Choose between System, Light, or Dark theme
4. Theme changes apply instantly

### Manual Control
1. Open the **Glyph Control** tab
2. Toggle individual glyphs on/off
3. Adjust brightness with sliders
4. Use preset buttons for quick brightness levels

## 🛠️ Technical Details

### Architecture
- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Root Access**: libsu library
- **State Management**: DataStore Preferences
- **Minimum SDK**: 31 (Android 12)
- **Target SDK**: 36

### Glyph Control Mechanism
MyGlyph directly controls the Glyph Interface by writing to system LED brightness files:
```
/sys/devices/platform/soc/984000.i2c/i2c-0/0-0020/leds/aw210xx_led/
```

This low-level access requires root permissions and provides precise control over each individual glyph.

## 📝 Changelog

### Version 1.2 (Latest)
- ✨ Complete Nothing OS design overhaul
- 🔲 Added 4 Quick Settings Tiles
- 🎨 Theme switcher (System/Light/Dark)
- 📝 Nothing typography integration (NDot57, NType82)
- 🔧 Improved UI spacing and readability
- 💾 DataStore implementation for preferences

### Version 1.0
- 🎛️ Individual glyph control
- ⚡ Quick brightness presets
- 📊 Real-time brightness display
- 🌊 Breathing effect
- 🎬 Multiple animation patterns

## ⚠️ Important Notes

- **Root Access**: This app requires root access. There is no way to control glyphs without root permissions due to system-level file access requirements.
- **Battery Impact**: Continuous glyph usage may affect battery life
- **Epilepsy Warning**: The "Epilepsy" effect uses rapid flashing and should be used with caution

## 🤝 Contributing

Suggestions for new features are actively welcomed! Feel free to:
- Open an issue on GitHub
- Submit a pull request
- Contact me on Telegram: [@username7052](https://t.me/username7052)

I respond in any language!

## 📄 License

This project is open source and available for personal use. Please credit the original author if you use or modify this code.

---

<div align="center">

**Enjoy your enhanced Glyph experience! ✨**

</div>
