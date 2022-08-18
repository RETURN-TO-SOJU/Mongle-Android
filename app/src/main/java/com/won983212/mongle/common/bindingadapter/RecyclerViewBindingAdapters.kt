package com.won983212.mongle.common.util

import androidx.databinding.BindingAdapter
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.won983212.mongle.common.base.BaseRecyclerAdapter
import com.won983212.mongle.common.base.BaseRecyclerItem

@Suppress("UNCHECKED_CAST")
@BindingAdapter("items")
fun setItems(view: RecyclerView, data: List<BaseRecyclerItem>?) {
    val adapter = view.adapter as? BaseRecyclerAdapter<ViewDataBinding, BaseRecyclerItem>?
    adapter?.set(data ?: emptyList())
}