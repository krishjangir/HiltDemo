package com.krishworld.hiltexample.ui.viewpager.utility

import com.krishworld.hiltexample.data.local.Videos

sealed class ViewPagerUiModel(
    open val viewType: ViewPagerViewType,
    open val viewId: String = ""
) {
    data class VideosUiModel(
        var videos: Videos
    ) : ViewPagerUiModel(viewType = ViewPagerViewType.ITEM_VIDEO)
}
