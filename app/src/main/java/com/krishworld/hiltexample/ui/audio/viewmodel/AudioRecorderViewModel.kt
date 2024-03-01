package com.krishworld.hiltexample.ui.audio.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.krishworld.hiltexample.base.BaseViewModel
import com.krishworld.hiltexample.base.Event
import com.krishworld.hiltexample.ui.audio.AudioRecorderEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AudioRecorderViewModel @Inject constructor() : BaseViewModel() {
    private val _localizationEventLiveData = MutableLiveData<Event<AudioRecorderEvent>>()
    val localizationEventLiveData: LiveData<Event<AudioRecorderEvent>>
        get() = _localizationEventLiveData

    fun startRecording() {
        _localizationEventLiveData.value = Event(AudioRecorderEvent.StartRecording)
    }

    fun stopRecording() {
        _localizationEventLiveData.value = Event(AudioRecorderEvent.StopRecording)
    }

    fun cancelRecording() {
        _localizationEventLiveData.value = Event(AudioRecorderEvent.CancelRecording)
    }
}