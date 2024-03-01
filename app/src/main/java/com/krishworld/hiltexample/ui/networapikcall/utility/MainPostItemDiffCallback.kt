package com.krishworld.hiltexample.ui.networapikcall.utility

import androidx.recyclerview.widget.DiffUtil

class MainPostItemDiffCallback : DiffUtil.ItemCallback<MainPostUiModel>() {
    override fun areItemsTheSame(
        oldItem: MainPostUiModel,
        newItem: MainPostUiModel
    ): Boolean {
        return oldItem.viewId == newItem.viewId
    }

    override fun areContentsTheSame(
        oldItem: MainPostUiModel,
        newItem: MainPostUiModel
    ): Boolean {
        return oldItem == newItem
    }
}