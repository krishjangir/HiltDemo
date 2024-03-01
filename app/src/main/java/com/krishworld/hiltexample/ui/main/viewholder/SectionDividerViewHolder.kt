package com.krishworld.hiltexample.ui.main.viewholder

import androidx.databinding.ViewDataBinding
import com.krishworld.hiltexample.base.BaseViewHolder
import com.krishworld.hiltexample.ui.main.MainUiModel
import com.krishworld.hiltexample.ui.main.viewmodel.MainViewModel

class SectionDividerViewHolder(
    private val binding: ViewDataBinding,
    private val mainViewModel: MainViewModel
) : BaseViewHolder<MainUiModel>(binding) {
    override fun bindData(dataModel: MainUiModel) {
        with(binding) {
            executePendingBindings()
        }
    }
}