package com.krishworld.hiltexample.ui.themes

sealed class ThemeEvent {
    object DarkMode : ThemeEvent()
    object LightMode : ThemeEvent()
    object SystemThemeMode : ThemeEvent()
}