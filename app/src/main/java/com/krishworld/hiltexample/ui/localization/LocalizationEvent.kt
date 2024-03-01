package com.krishworld.hiltexample.ui.localization

sealed class LocalizationEvent {
    data class ChangeLanguageEvent(val language: String) : LocalizationEvent()
}
