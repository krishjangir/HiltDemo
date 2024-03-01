package com.krishworld.hiltexample.ui.main.adapter

import androidx.recyclerview.widget.DiffUtil
import com.krishworld.hiltexample.ui.main.MainUiModel

class MainItemDiffCallback : DiffUtil.ItemCallback<MainUiModel>() {
    override fun areItemsTheSame(
        oldItem: MainUiModel,
        newItem: MainUiModel
    ): Boolean {
        return oldItem.viewId == newItem.viewId
    }

    override fun areContentsTheSame(
        oldItem: MainUiModel,
        newItem: MainUiModel
    ): Boolean {
        return oldItem == newItem
    }
}