package com.krishworld.hiltexample.ui.viewpager.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.krishworld.hiltexample.base.BaseViewModel
import com.krishworld.hiltexample.base.Event
import com.krishworld.hiltexample.ui.viewpager.ViewPagerEvent
import com.krishworld.hiltexample.ui.viewpager.usecase.ViewPagerVideosUseCase
import com.krishworld.hiltexample.ui.viewpager.utility.ViewPagerUiModel
import com.krishworld.hiltexample.ui.viewpager.utility.ViewPagerVideoSection
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ViewPagerViewModel @Inject constructor(val viewPagerVideosUseCase: ViewPagerVideosUseCase) :
    BaseViewModel() {
    // Video Ui Components
    private val _videoContentLiveData = MutableLiveData<List<ViewPagerUiModel>>()
    val videoContentLiveData: LiveData<List<ViewPagerUiModel>>
        get() = _videoContentLiveData

    // ViewPager Events
    private val _launchEventLiveData = MutableLiveData<Event<ViewPagerEvent>>()
    val launchEventLiveData: LiveData<Event<ViewPagerEvent>>
        get() = _launchEventLiveData

    // UI sections
    private var sections = mutableMapOf<ViewPagerVideoSection, List<ViewPagerUiModel>>()

    init {
        fetchDataAndUpdateUI()
    }

    // fetching UI data here
    private fun fetchDataAndUpdateUI() {
        val sectionData = viewPagerVideosUseCase.getVideosUiData()
        sections = sectionData as MutableMap<ViewPagerVideoSection, List<ViewPagerUiModel>>
        _videoContentLiveData.value = sections.values.flatten()
    }

    fun playVideo() {
        _launchEventLiveData.value = Event(ViewPagerEvent.PlayVideo)
    }

    fun playPauseVideo() {
        _launchEventLiveData.value = Event(ViewPagerEvent.PlayPauseVideo)
    }

    fun pauseVideo() {
        _launchEventLiveData.value = Event(ViewPagerEvent.PauseVideo)
    }
}