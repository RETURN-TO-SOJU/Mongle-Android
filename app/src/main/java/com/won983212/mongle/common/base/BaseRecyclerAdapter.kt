package com.won983212.mongle.common.base

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * RecyclerView에 사용될 Base Adapter이다.
 */
abstract class BaseRecyclerAdapter<B : ViewDataBinding, T : BaseRecyclerItem>(
    var data: List<T> = listOf()
) : RecyclerView.Adapter<BaseRecyclerViewHolder<B>>() {

    @get:LayoutRes
    abstract val itemLayoutId: Int

    abstract fun bind(binding: B, item: T)

    // TODO change it to difftool logic
    @SuppressLint("NotifyDataSetChanged")
    fun set(list: List<T>) {
        this.data = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRecyclerViewHolder<B> {
        val binding = DataBindingUtil.inflate<B>(
            LayoutInflater.from(parent.context),
            itemLayoutId,
            parent,
            false
        )
        return BaseRecyclerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseRecyclerViewHolder<B>, position: Int) {
        bind(holder.binding, data[position])
    }

    override fun getItemCount(): Int = data.size
}