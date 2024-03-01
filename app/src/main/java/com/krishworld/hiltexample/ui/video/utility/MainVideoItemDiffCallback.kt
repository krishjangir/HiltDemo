package com.krishworld.hiltexample.ui.video.utility

import androidx.recyclerview.widget.DiffUtil

class MainVideoItemDiffCallback : DiffUtil.ItemCallback<MainVideoUiModel>() {
    override fun areItemsTheSame(
        oldItem: MainVideoUiModel,
        newItem: MainVideoUiModel
    ): Boolean {
        return oldItem.viewId == newItem.viewId
    }

    override fun areContentsTheSame(
        oldItem: MainVideoUiModel,
        newItem: MainVideoUiModel
    ): Boolean {
        return oldItem == newItem
    }
}