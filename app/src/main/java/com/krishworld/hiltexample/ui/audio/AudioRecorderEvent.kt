package com.krishworld.hiltexample.ui.audio

sealed class AudioRecorderEvent {
    object StartRecording : AudioRecorderEvent()
    object StopRecording : AudioRecorderEvent()
    object CancelRecording : AudioRecorderEvent()
}