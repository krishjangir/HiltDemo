package com.krishworld.hiltexample.base

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * Runtime binding for View to specific data type T without explicit casting
 */
abstract class BaseViewHolder<T>(binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
    abstract fun bindData(dataModel: T)
}