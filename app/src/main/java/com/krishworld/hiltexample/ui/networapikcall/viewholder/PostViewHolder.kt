package com.krishworld.hiltexample.ui.networapikcall.viewholder

import com.krishworld.hiltexample.BR
import com.krishworld.hiltexample.base.BaseViewHolder
import com.krishworld.hiltexample.databinding.ItemPostViewBinding
import com.krishworld.hiltexample.ui.networapikcall.utility.MainPostUiModel
import com.krishworld.hiltexample.ui.networapikcall.viewmodel.NetworkApiViewModel

class PostViewHolder(
    internal val binding: ItemPostViewBinding,
    private val networkApiViewModel: NetworkApiViewModel
) :
    BaseViewHolder<MainPostUiModel.PostUiModel>(binding) {
    override fun bindData(dataModel: MainPostUiModel.PostUiModel) {
        with(binding) {
            setVariable(BR.viewModel, networkApiViewModel)
            setVariable(BR.postData, dataModel)
            executePendingBindings()
        }
    }
}