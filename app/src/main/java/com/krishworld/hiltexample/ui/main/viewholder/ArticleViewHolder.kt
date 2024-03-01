package com.krishworld.hiltexample.ui.main.viewholder

import androidx.databinding.ViewDataBinding
import com.krishworld.hiltexample.base.BaseViewHolder
import com.krishworld.hiltexample.ui.main.MainUiModel
import com.krishworld.hiltexample.ui.main.viewmodel.MainViewModel
import com.krishworld.hiltexample.BR

class ArticleViewHolder(
    private val binding: ViewDataBinding,
    private val mainViewModel: MainViewModel
) :
    BaseViewHolder<MainUiModel.ArticlesUiModel>(binding) {
    override fun bindData(dataModel: MainUiModel.ArticlesUiModel) {
        with(binding) {
            setVariable(BR.viewModel, mainViewModel)
            setVariable(BR.articleData, dataModel)
            executePendingBindings()
        }
    }
}