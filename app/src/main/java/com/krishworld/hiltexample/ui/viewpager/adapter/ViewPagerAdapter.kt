package com.krishworld.hiltexample.ui.viewpager.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.krishworld.hiltexample.base.BaseViewHolder
import com.krishworld.hiltexample.ui.viewpager.utility.ViewPagerUiModel
import com.krishworld.hiltexample.ui.viewpager.utility.ViewPagerViewType
import com.krishworld.hiltexample.ui.viewpager.viewmodel.ViewPagerViewModel

class ViewPagerAdapter(
    private val viewModel: ViewPagerViewModel
) : ListAdapter<ViewPagerUiModel, BaseViewHolder<ViewPagerUiModel>>(ViewpagerDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewPagerViewType.values()[viewType].createViewHolder(
            parent,
            LayoutInflater.from(parent.context),
            viewModel
        )

    override fun onBindViewHolder(holder: BaseViewHolder<ViewPagerUiModel>, position: Int) {
        holder.bindData(getItem(position))
    }

    override fun getItemCount(): Int = currentList.size

    override fun getItemId(position: Int) = position.toLong()

    override fun getItemViewType(position: Int) = getItem(position).viewType.ordinal
}