package com.krishworld.hiltexample.data.remote

import okhttp3.ResponseBody

sealed class Result<out T> {
    data class Success<out T>(val value: T) : Result<T>()
    data class Failure(
        val isNetworkError: Boolean,
        val errorCode: Int?,
        val errorBody: ResponseBody?
    ) : Result<Nothing>()

    object Loading : Result<Nothing>()
}