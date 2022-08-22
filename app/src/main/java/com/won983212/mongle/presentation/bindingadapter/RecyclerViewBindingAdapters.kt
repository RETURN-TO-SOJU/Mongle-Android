package com.won983212.mongle.common.util

import androidx.databinding.BindingAdapter
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.won983212.mongle.presentation.base.BaseRecyclerAdapter

@Suppress("UNCHECKED_CAST")
@BindingAdapter("items")
fun setItems(view: RecyclerView, data: List<Any>?) {
    val adapter = view.adapter as? BaseRecyclerAdapter<ViewDataBinding, Any>?
    adapter?.set(data ?: emptyList())
}