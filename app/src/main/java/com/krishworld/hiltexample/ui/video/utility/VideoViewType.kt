package com.krishworld.hiltexample.ui.video.utility

import android.view.LayoutInflater
import android.view.ViewGroup
import com.krishworld.hiltexample.base.BaseViewHolder
import com.krishworld.hiltexample.databinding.ItemVideoViewBinding
import com.krishworld.hiltexample.ui.networapikcall.utility.MainPostUiModel
import com.krishworld.hiltexample.ui.video.viewmodel.VideoViewModel
import com.krishworld.hiltexample.ui.video.viewholder.VideosViewHolder

enum class VideoViewType {
    ITEM_VIDEO {
        override fun createViewHolder(
            parent: ViewGroup,
            layoutInflater: LayoutInflater,
            videoViewModel: VideoViewModel
        ): BaseViewHolder<MainVideoUiModel> {
            return VideosViewHolder(
                ItemVideoViewBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                videoViewModel
            ) as BaseViewHolder<MainVideoUiModel>
        }
    };

    abstract fun createViewHolder(
        parent: ViewGroup,
        layoutInflater: LayoutInflater,
        videoViewModel: VideoViewModel
    ): BaseViewHolder<MainVideoUiModel>
}