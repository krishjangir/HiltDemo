package com.krishworld.hiltexample.ui.video

import com.krishworld.hiltexample.ui.video.utility.MainVideoUiModel

sealed class VideoEvent {
    data class PlayVideo(val video: MainVideoUiModel.VideosUiModel) : VideoEvent()
    object PauseVideo : VideoEvent()
    object PlayPauseVideo : VideoEvent()
}