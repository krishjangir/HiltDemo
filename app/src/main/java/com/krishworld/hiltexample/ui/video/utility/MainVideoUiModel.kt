package com.krishworld.hiltexample.ui.video.utility

import com.krishworld.hiltexample.data.local.Videos

sealed class MainVideoUiModel(
    open val viewType: VideoViewType,
    open val viewId: String = ""
) {
    data class VideosUiModel(
        var videos: Videos
    ) : MainVideoUiModel(viewType = VideoViewType.ITEM_VIDEO)
}