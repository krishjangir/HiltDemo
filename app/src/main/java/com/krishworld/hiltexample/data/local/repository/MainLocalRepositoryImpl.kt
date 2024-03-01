package com.krishworld.hiltexample.data.local.repository

import android.content.Context
import com.google.gson.Gson
import com.krishworld.hiltexample.R
import com.krishworld.hiltexample.data.local.ArticlesRawData
import com.krishworld.hiltexample.data.local.VideosRawData
import javax.inject.Inject

class MainLocalRepositoryImpl @Inject constructor(
    private val context: Context,
    private val gson: Gson
) : MainLocalRepository {

    override fun getArticlesRawData(): ArticlesRawData {
        val jsonContent =
            context.resources.openRawResource(R.raw.articles)
                .bufferedReader()
                .use { it.readText() }
        return gson.fromJson(jsonContent, ArticlesRawData::class.java)
    }

    override fun getVideosRawData(): VideosRawData {
        val jsonContent =
            context.resources.openRawResource(R.raw.videos)
                .bufferedReader()
                .use { it.readText() }
        return gson.fromJson(jsonContent, VideosRawData::class.java)
    }
}