package com.krishworld.hiltexample.data.model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity("Posts")
data class Post(
    @SerializedName("userId")
    var userId: Int? = 0,
    @SerializedName("id")
    var id: Int? = 0,
    @SerializedName("title")
    var title: String? = "",
    @SerializedName("body")
    var body: String? = ""
)