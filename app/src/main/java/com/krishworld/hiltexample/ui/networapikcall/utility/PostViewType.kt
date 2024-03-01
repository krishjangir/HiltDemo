package com.krishworld.hiltexample.ui.networapikcall.utility

import android.view.LayoutInflater
import android.view.ViewGroup
import com.krishworld.hiltexample.base.BaseViewHolder
import com.krishworld.hiltexample.databinding.ItemPostViewBinding
import com.krishworld.hiltexample.databinding.ItemVideoViewBinding
import com.krishworld.hiltexample.ui.networapikcall.viewholder.PostViewHolder
import com.krishworld.hiltexample.ui.networapikcall.viewmodel.NetworkApiViewModel
import com.krishworld.hiltexample.ui.video.viewmodel.VideoViewModel
import com.krishworld.hiltexample.ui.video.viewholder.VideosViewHolder

enum class PostViewType {
    ITEM_POST {
        override fun createViewHolder(
            parent: ViewGroup,
            layoutInflater: LayoutInflater,
            networkApiViewModel: NetworkApiViewModel
        ): BaseViewHolder<MainPostUiModel> {
            return PostViewHolder(
                ItemPostViewBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                networkApiViewModel
            ) as BaseViewHolder<MainPostUiModel>
        }
    };

    abstract fun createViewHolder(
        parent: ViewGroup,
        layoutInflater: LayoutInflater,
        networkApiViewModel: NetworkApiViewModel
    ): BaseViewHolder<MainPostUiModel>
}