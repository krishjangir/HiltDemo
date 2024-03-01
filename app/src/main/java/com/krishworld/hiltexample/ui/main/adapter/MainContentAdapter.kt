package com.krishworld.hiltexample.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.krishworld.hiltexample.base.BaseViewHolder
import com.krishworld.hiltexample.ui.main.MainRvViewType
import com.krishworld.hiltexample.ui.main.MainUiModel
import com.krishworld.hiltexample.ui.main.viewmodel.MainViewModel

class MainContentAdapter(
    private val viewModel: MainViewModel
) : ListAdapter<MainUiModel, BaseViewHolder<MainUiModel>>(MainItemDiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<MainUiModel> {
        val inflater = LayoutInflater.from(parent.context)
        return MainRvViewType.values()[viewType].createViewHolder(
            parent,
            inflater,
            viewModel
        )
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder<MainUiModel>,
        position: Int
    ) {
        holder.bindData(getItem(position))
    }

    override fun getItemViewType(position: Int): Int = getItem(position).viewType.ordinal
}