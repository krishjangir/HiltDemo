package com.krishworld.hiltexample.ui.networapikcall.utility

import com.krishworld.hiltexample.data.model.Post

sealed class MainPostUiModel(
    open val viewType: PostViewType,
    open val viewId: String = ""
) {
    data class PostUiModel(
        var posts: Post
    ) : MainPostUiModel(viewType = PostViewType.ITEM_POST)
}