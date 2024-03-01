package com.krishworld.hiltexample.data.local.repository

import com.krishworld.hiltexample.data.local.ArticlesRawData
import com.krishworld.hiltexample.data.local.VideosRawData


interface MainLocalRepository {
    fun getArticlesRawData(): ArticlesRawData
    fun getVideosRawData(): VideosRawData
}