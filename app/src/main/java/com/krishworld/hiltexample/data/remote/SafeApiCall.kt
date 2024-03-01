package com.krishworld.hiltexample.data.remote

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

object SafeApiCall {
    suspend fun <T> apiCall(
        apiCall: suspend () -> T
    ): Result<T> {
        return withContext(Dispatchers.IO) {
            try {
                Result.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is HttpException -> {
                        Result.Failure(false, throwable.code(), throwable.response()?.errorBody())
                    }
                    else -> {
                        Result.Failure(true, null, null)
                    }
                }
            }
        }
    }
}