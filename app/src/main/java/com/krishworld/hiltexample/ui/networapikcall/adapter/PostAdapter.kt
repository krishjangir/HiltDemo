package com.krishworld.hiltexample.ui.networapikcall.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.krishworld.hiltexample.base.BaseViewHolder
import com.krishworld.hiltexample.ui.networapikcall.utility.MainPostItemDiffCallback
import com.krishworld.hiltexample.ui.networapikcall.utility.MainPostUiModel
import com.krishworld.hiltexample.ui.networapikcall.utility.PostViewType
import com.krishworld.hiltexample.ui.networapikcall.viewmodel.NetworkApiViewModel
import javax.inject.Inject

class PostAdapter @Inject constructor(private val viewModel: NetworkApiViewModel) :
    ListAdapter<MainPostUiModel, BaseViewHolder<MainPostUiModel>>(MainPostItemDiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<MainPostUiModel> {
        val inflater = LayoutInflater.from(parent.context)
        return PostViewType.values()[viewType].createViewHolder(
            parent,
            inflater,
            viewModel
        )
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder<MainPostUiModel>,
        position: Int
    ) {
        holder.bindData(getItem(position))
    }

    override fun getItemViewType(position: Int): Int = getItem(position).viewType.ordinal
}