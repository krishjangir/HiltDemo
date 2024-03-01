package com.krishworld.hiltexample.ui.dialog.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.krishworld.hiltexample.base.BaseViewModel
import com.krishworld.hiltexample.base.Event
import com.krishworld.hiltexample.ui.dialog.DialogLaunchEvent
import com.krishworld.hiltexample.ui.main.LaunchEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DialogTestViewModel @Inject constructor() : BaseViewModel() {

    // DialogLaunchEvent components
    private val _launchEventLiveData = MutableLiveData<Event<DialogLaunchEvent>>()
    val launchEventLiveData: LiveData<Event<DialogLaunchEvent>>
        get() = _launchEventLiveData

    fun showAlertDialog() {
        _launchEventLiveData.value = Event(DialogLaunchEvent.LaunchAlertDialog)
    }

    fun showCustomDialog() {
        _launchEventLiveData.value = Event(DialogLaunchEvent.LaunchCustomDialog)
    }

    fun showFullScreenDialog() {
        _launchEventLiveData.value = Event(DialogLaunchEvent.LaunchFullScreenDialog)
    }

    fun showBottomSheetDialog() {
        _launchEventLiveData.value = Event(DialogLaunchEvent.LaunchBottomSheetDialog)
    }
}