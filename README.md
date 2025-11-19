# MyGlyph 1.2

Full control over glyphs.

### New in 1.2
- ✨ Complete Nothing OS design overhaul
- 🔲 Added 4 Quick Settings Tiles
- 🎨 Theme switcher (System/Light/Dark)
- 📝 Nothing typography integration (NDot57, NType82)
- 🔧 Improved UI spacing and readability
- 💾 DataStore implementation for preferences

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


## Glyph Control Mechanism
MyGlyph directly controls the Glyph Interface by writing to system LED brightness files:
```
/sys/devices/platform/soc/984000.i2c/i2c-0/0-0020/leds/aw210xx_led/
```

This low-level access requires root permissions and provides precise control over each individual glyph.


## 🤝 Contributing

Suggestions for new features are actively welcomed! Feel free to:
- Open an issue on GitHub
- Submit a pull request
- Contact me on Telegram: [@username7052](https://t.me/username7052)

I respond in any language!


