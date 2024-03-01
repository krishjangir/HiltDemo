package com.krishworld.hiltexample.ui.themes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.krishworld.hiltexample.base.BaseViewModel
import com.krishworld.hiltexample.base.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor() : BaseViewModel() {
    // Theme Events
    private val _launchEventLiveData = MutableLiveData<Event<ThemeEvent>>()
    val launchEventLiveData: LiveData<Event<ThemeEvent>>
        get() = _launchEventLiveData

    fun setDarkMode() {
        _launchEventLiveData.value = Event(ThemeEvent.DarkMode)
    }

    fun setLightMode() {
        _launchEventLiveData.value = Event(ThemeEvent.LightMode)
    }

    fun setSystemThemeMode() {
        _launchEventLiveData.value = Event(ThemeEvent.SystemThemeMode)
    }
}