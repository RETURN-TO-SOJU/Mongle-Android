package com.won983212.mongle.presentation.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

/**
 * RecyclerView에 사용될 Base Adapter이다.
 */
abstract class BaseRecyclerAdapter<B : ViewDataBinding, T>(
    var data: List<T> = listOf()
) : RecyclerView.Adapter<BaseRecyclerViewHolder<B>>() {

    @get:LayoutRes
    protected abstract val itemLayoutId: Int

    protected abstract fun bind(binding: B, item: T)

    protected lateinit var parent: ViewGroup

    fun set(newList: List<T>) {
        val oldList = this.data
        this.data = newList

        val diffCallback = AdapterDiffCallback(oldList, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRecyclerViewHolder<B> {
        val binding = DataBindingUtil.inflate<B>(
            LayoutInflater.from(parent.context),
            itemLayoutId,
            parent,
            false
        )
        this.parent = parent
        return BaseRecyclerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseRecyclerViewHolder<B>, position: Int) {
        bind(holder.binding, data[position])
    }

    override fun getItemCount(): Int = data.size
}