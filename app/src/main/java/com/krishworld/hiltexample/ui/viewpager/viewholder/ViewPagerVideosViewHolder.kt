package com.krishworld.hiltexample.ui.viewpager.viewholder

import com.krishworld.hiltexample.BR
import com.krishworld.hiltexample.base.BaseViewHolder
import com.krishworld.hiltexample.databinding.ItemViewpagerBinding
import com.krishworld.hiltexample.ui.viewpager.utility.ViewPagerUiModel
import com.krishworld.hiltexample.ui.viewpager.viewmodel.ViewPagerViewModel

class ViewPagerVideosViewHolder(
    internal val binding: ItemViewpagerBinding,
    private val videoViewModel: ViewPagerViewModel
) :
    BaseViewHolder<ViewPagerUiModel.VideosUiModel>(binding) {
    override fun bindData(dataModel: ViewPagerUiModel.VideosUiModel) {
        with(binding) {
            setVariable(BR.viewModel, videoViewModel)
            setVariable(BR.video, dataModel)
            executePendingBindings()
        }
    }
}