package com.krishworld.hiltexample.ui.networapikcall.usecase

import com.krishworld.hiltexample.data.model.Post
import com.krishworld.hiltexample.data.remote.Result
import com.krishworld.hiltexample.ui.networapikcall.utility.MainPostUiModel
import com.krishworld.hiltexample.ui.networapikcall.utility.PostSection

interface NetworkApiUseCase {
    suspend fun getUserPosts(): Result<List<Post>>
    suspend fun getUserPostsUiData(): Map<PostSection, List<MainPostUiModel>>
}