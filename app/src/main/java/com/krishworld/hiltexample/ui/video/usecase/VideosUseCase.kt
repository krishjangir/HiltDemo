package com.krishworld.hiltexample.ui.video.usecase


import com.krishworld.hiltexample.ui.video.utility.MainVideoUiModel
import com.krishworld.hiltexample.ui.video.utility.VideoSection


interface VideosUseCase {
    fun getVideosUiData(): Map<VideoSection, List<MainVideoUiModel>>
}