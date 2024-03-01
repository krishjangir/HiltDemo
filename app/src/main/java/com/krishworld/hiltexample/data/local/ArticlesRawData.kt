package com.krishworld.hiltexample.data.local

import com.google.gson.annotations.SerializedName


data class ArticlesRawData(
    @SerializedName("articles") var articles: ArrayList<Articles> = arrayListOf(),
)

data class Articles(
    @SerializedName("title") var title: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("imageUrl") var imageUrl: String? = null,
    @SerializedName("webUrl") var webUrl: String? = null
)