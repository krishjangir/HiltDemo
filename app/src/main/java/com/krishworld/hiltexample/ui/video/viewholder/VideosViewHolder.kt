package com.krishworld.hiltexample.ui.video.viewholder

import com.krishworld.hiltexample.base.BaseViewHolder
import com.krishworld.hiltexample.databinding.ItemVideoViewBinding
import com.krishworld.hiltexample.BR
import com.krishworld.hiltexample.ui.networapikcall.utility.MainPostUiModel
import com.krishworld.hiltexample.ui.video.utility.MainVideoUiModel
import com.krishworld.hiltexample.ui.video.viewmodel.VideoViewModel

class VideosViewHolder(
    internal val binding: ItemVideoViewBinding,
    private val videoViewModel: VideoViewModel
) :
    BaseViewHolder<MainVideoUiModel.VideosUiModel>(binding) {
    override fun bindData(dataModel: MainVideoUiModel.VideosUiModel) {
        with(binding) {
            setVariable(BR.viewModel, videoViewModel)
            setVariable(BR.video, dataModel)
            executePendingBindings()
        }
    }
}

