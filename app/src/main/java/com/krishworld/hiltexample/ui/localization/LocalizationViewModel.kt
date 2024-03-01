package com.krishworld.hiltexample.ui.localization

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.krishworld.hiltexample.base.BaseViewModel
import com.krishworld.hiltexample.base.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LocalizationViewModel @Inject constructor() : BaseViewModel() {

    private val _localizationEventLiveData = MutableLiveData<Event<LocalizationEvent>>()
    val localizationEventLiveData: LiveData<Event<LocalizationEvent>>
        get() = _localizationEventLiveData


    fun setEnglish() {
        _localizationEventLiveData.value = Event(LocalizationEvent.ChangeLanguageEvent("en"))
    }

    fun setFrench() {
        _localizationEventLiveData.value = Event(LocalizationEvent.ChangeLanguageEvent("fr"))
    }

    fun setHindi() {
        _localizationEventLiveData.value = Event(LocalizationEvent.ChangeLanguageEvent("hi"))
    }
}