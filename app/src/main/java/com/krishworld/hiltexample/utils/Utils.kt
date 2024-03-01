package com.krishworld.hiltexample.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.krishworld.hiltexample.R

object Utils {
    @JvmStatic
    @BindingAdapter("imageUrl")
    fun loadImage(view: ImageView, imageUrl: String) {
        Glide.with(view.context)
            .load(imageUrl)
            .apply(RequestOptions().error(R.drawable.ic_launcher_background))
            .into(view)
    }

    /**
     *  This helper is to make a lightweight way to say you meant to match all of them.
     * */
    val <T> T.exhaustive: T
        get() = this
}