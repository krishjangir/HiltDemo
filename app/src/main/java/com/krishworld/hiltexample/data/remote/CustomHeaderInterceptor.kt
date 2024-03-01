package com.krishworld.hiltexample.data.remote

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class CustomHeaderInterceptor @Inject constructor() : Interceptor {
    private val contentType = "Content-Type"
    private val contentTypeValue = "application/json; charset=UTF-8"
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val modifiedRequest = originalRequest.newBuilder()
            .header(contentType, contentTypeValue)
            .method(originalRequest.method, originalRequest.body)
            .build()
        return chain.proceed(modifiedRequest)
    }
}