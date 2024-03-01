package com.krishworld.hiltexample.data.remote

import ApiEndPoint
import com.krishworld.hiltexample.data.model.Post
import retrofit2.http.GET

interface ApiService {
    @GET(ApiEndPoint.POSTS)
    suspend fun fetchAllPosts(): List<Post>
}