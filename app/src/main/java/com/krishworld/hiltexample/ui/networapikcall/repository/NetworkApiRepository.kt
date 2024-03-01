package com.krishworld.hiltexample.ui.networapikcall.repository

import com.krishworld.hiltexample.data.model.Post
import com.krishworld.hiltexample.data.remote.Result

interface NetworkApiRepository {
    suspend fun getUserPosts(): Result<List<Post>>
}