package com.krishworld.hiltexample.ui.viewpager.utility

import android.view.LayoutInflater
import android.view.ViewGroup
import com.krishworld.hiltexample.base.BaseViewHolder
import com.krishworld.hiltexample.databinding.ItemViewpagerBinding
import com.krishworld.hiltexample.ui.viewpager.viewholder.ViewPagerVideosViewHolder
import com.krishworld.hiltexample.ui.viewpager.viewmodel.ViewPagerViewModel

enum class ViewPagerViewType {
    ITEM_VIDEO {
        override fun createViewHolder(
            parent: ViewGroup,
            layoutInflater: LayoutInflater,
            videoViewModel: ViewPagerViewModel
        ): BaseViewHolder<ViewPagerUiModel> {
            return ViewPagerVideosViewHolder(
                ItemViewpagerBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                videoViewModel
            ) as BaseViewHolder<ViewPagerUiModel>
        }
    };

    abstract fun createViewHolder(
        parent: ViewGroup,
        layoutInflater: LayoutInflater,
        videoViewModel: ViewPagerViewModel
    ): BaseViewHolder<ViewPagerUiModel>
}