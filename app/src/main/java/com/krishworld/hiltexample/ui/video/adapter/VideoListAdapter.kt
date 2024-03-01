package com.krishworld.hiltexample.ui.video.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.krishworld.hiltexample.base.BaseViewHolder
import com.krishworld.hiltexample.ui.video.utility.MainVideoItemDiffCallback
import com.krishworld.hiltexample.ui.video.viewmodel.VideoViewModel
import com.krishworld.hiltexample.ui.video.utility.MainVideoUiModel
import com.krishworld.hiltexample.ui.video.utility.VideoViewType


class VideoListAdapter(
    private val viewModel: VideoViewModel
) :
    ListAdapter<MainVideoUiModel, BaseViewHolder<MainVideoUiModel>>(MainVideoItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VideoViewType.values()[viewType].createViewHolder(
            parent,
            LayoutInflater.from(parent.context),
            viewModel
        )

    override fun onBindViewHolder(holder: BaseViewHolder<MainVideoUiModel>, position: Int) {
        holder.bindData(getItem(position))
    }

    override fun getItemCount(): Int = currentList.size

    override fun getItemId(position: Int) = position.toLong()

    override fun getItemViewType(position: Int) = getItem(position).viewType.ordinal

}