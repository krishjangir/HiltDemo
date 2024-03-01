package com.krishworld.hiltexample.data.local

import com.google.gson.annotations.SerializedName


data class VideosRawData(
    @SerializedName("videos") var videos: ArrayList<Videos> = arrayListOf(),
)

data class Videos(
    @SerializedName("title") var title: String? = null,
    @SerializedName("subtitle") var subtitle: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("thumb") var thumb: String? = null,
    @SerializedName("videoUrl") var videoUrl: String? = null
)

