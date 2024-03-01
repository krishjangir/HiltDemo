package com.krishworld.hiltexample.ui.video.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.krishworld.hiltexample.base.BaseViewModel
import com.krishworld.hiltexample.base.Event
import com.krishworld.hiltexample.ui.video.VideoEvent
import com.krishworld.hiltexample.ui.video.usecase.VideosUseCase
import com.krishworld.hiltexample.ui.video.utility.MainVideoUiModel
import com.krishworld.hiltexample.ui.video.utility.VideoSection
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor(val videosUseCase: VideosUseCase) : BaseViewModel() {
    // Video Ui Components
    private val _videoContentLiveData = MutableLiveData<List<MainVideoUiModel>>()
    val videoContentLiveData: LiveData<List<MainVideoUiModel>>
        get() = _videoContentLiveData

    // UI sections
    private var sections = mutableMapOf<VideoSection, List<MainVideoUiModel>>()

    // Video Events
    private val _launchEventLiveData = MutableLiveData<Event<VideoEvent>>()
    val launchEventLiveData: LiveData<Event<VideoEvent>>
        get() = _launchEventLiveData

    init {
        fetchDataAndUpdateUI()
    }

    // fetching UI data here
    private fun fetchDataAndUpdateUI() {
        val sectionData = videosUseCase.getVideosUiData()
        sections = sectionData as MutableMap<VideoSection, List<MainVideoUiModel>>
        _videoContentLiveData.value = sections.values.flatten()
    }

    fun playVideo(video: MainVideoUiModel.VideosUiModel) {
        _launchEventLiveData.value = Event(VideoEvent.PlayVideo(video))
    }

    fun playPauseVideo() {
        _launchEventLiveData.value = Event(VideoEvent.PlayPauseVideo)
    }

    fun pauseVideo() {
        _launchEventLiveData.value = Event(VideoEvent.PauseVideo)
    }
}