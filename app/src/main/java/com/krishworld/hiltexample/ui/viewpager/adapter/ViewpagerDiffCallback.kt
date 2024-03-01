package com.krishworld.hiltexample.ui.viewpager.adapter

import androidx.recyclerview.widget.DiffUtil
import com.krishworld.hiltexample.ui.viewpager.utility.ViewPagerUiModel

class ViewpagerDiffCallback : DiffUtil.ItemCallback<ViewPagerUiModel>() {
    override fun areItemsTheSame(
        oldItem: ViewPagerUiModel,
        newItem: ViewPagerUiModel
    ): Boolean {
        return oldItem.viewId == newItem.viewId
    }

    override fun areContentsTheSame(
        oldItem: ViewPagerUiModel,
        newItem: ViewPagerUiModel
    ): Boolean {
        return oldItem == newItem
    }
}