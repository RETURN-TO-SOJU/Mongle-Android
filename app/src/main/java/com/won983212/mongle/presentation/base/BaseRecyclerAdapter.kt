package com.won983212.mongle.presentation.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView

/**
 * RecyclerView에 사용될 Base Adapter이다.
 */
abstract class BaseRecyclerAdapter<B : ViewDataBinding, T : Any> :
    RecyclerView.Adapter<BaseRecyclerViewHolder<B>>() {

    @get:LayoutRes
    protected abstract val itemLayoutId: Int
    protected abstract fun bind(binding: B, item: T)

    protected lateinit var parent: ViewGroup
    private val differ by lazy { AsyncListDiffer(this, AdapterDiffCallback<T>()) }
    protected val list: List<T>
        get() = differ.currentList

    fun set(newList: List<T>) {
        differ.submitList(newList)
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
        bind(holder.binding, differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size
}