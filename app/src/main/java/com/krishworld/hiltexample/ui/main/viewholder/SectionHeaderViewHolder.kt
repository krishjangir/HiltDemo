package com.krishworld.hiltexample.ui.main.viewholder

import androidx.databinding.ViewDataBinding
import com.krishworld.hiltexample.BR
import com.krishworld.hiltexample.base.BaseViewHolder
import com.krishworld.hiltexample.ui.main.MainUiModel
import com.krishworld.hiltexample.ui.main.viewmodel.MainViewModel

class SectionHeaderViewHolder(
    private val binding: ViewDataBinding,
    private val mainViewModel: MainViewModel
) :
    BaseViewHolder<MainUiModel.SectionHeaderUiModel>(binding) {
    override fun bindData(dataModel: MainUiModel.SectionHeaderUiModel) {
        with(binding) {
            setVariable(BR.viewModel, mainViewModel)
            setVariable(BR.sectionHeaderData, dataModel)
            executePendingBindings()
        }
    }
}