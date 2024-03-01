package com.krishworld.hiltexample.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import com.krishworld.hiltexample.base.BaseViewHolder
import com.krishworld.hiltexample.databinding.ItemArticleViewBinding
import com.krishworld.hiltexample.databinding.ItemSectionDividerBinding
import com.krishworld.hiltexample.databinding.ItemSectionHeaderBinding
import com.krishworld.hiltexample.ui.main.viewholder.ArticleViewHolder
import com.krishworld.hiltexample.ui.main.viewholder.SectionDividerViewHolder
import com.krishworld.hiltexample.ui.main.viewholder.SectionHeaderViewHolder
import com.krishworld.hiltexample.ui.main.viewmodel.MainViewModel

@Suppress("UNCHECKED_CAST")
enum class MainRvViewType {
    ITEM_ARTICLE {
        override fun createViewHolder(
            parent: ViewGroup,
            layoutInflater: LayoutInflater,
            mainViewModel: MainViewModel
        ): BaseViewHolder<MainUiModel> {
            return ArticleViewHolder(
                ItemArticleViewBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                mainViewModel
            ) as BaseViewHolder<MainUiModel>
        }
    },
    ITEM_SECTION_HEADER {
        override fun createViewHolder(
            parent: ViewGroup,
            layoutInflater: LayoutInflater,
            mainViewModel: MainViewModel
        ): BaseViewHolder<MainUiModel> {
            return SectionHeaderViewHolder(
                ItemSectionHeaderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                mainViewModel
            ) as BaseViewHolder<MainUiModel>
        }
    },
    ITEM_SECTION_DIVIDER {
        override fun createViewHolder(
            parent: ViewGroup,
            layoutInflater: LayoutInflater,
            mainViewModel: MainViewModel
        ): BaseViewHolder<MainUiModel> {
            return SectionDividerViewHolder(
                ItemSectionDividerBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                mainViewModel
            )
        }
    };

    abstract fun createViewHolder(
        parent: ViewGroup,
        layoutInflater: LayoutInflater,
        mainViewModel: MainViewModel
    ): BaseViewHolder<MainUiModel>
}


