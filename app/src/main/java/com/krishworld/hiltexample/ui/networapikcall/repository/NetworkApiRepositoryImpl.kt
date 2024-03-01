package com.krishworld.hiltexample.ui.networapikcall.repository

import com.krishworld.hiltexample.data.remote.ApiService
import com.krishworld.hiltexample.data.remote.SafeApiCall
import javax.inject.Inject

class NetworkApiRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    NetworkApiRepository {
    override suspend fun getUserPosts() = SafeApiCall.apiCall { apiService.fetchAllPosts() }
}