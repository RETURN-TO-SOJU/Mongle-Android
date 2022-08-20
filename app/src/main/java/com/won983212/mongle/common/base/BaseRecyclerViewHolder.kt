package com.won983212.mongle.common.base

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

open class BaseRecyclerViewHolder<B : ViewDataBinding>(
    val binding: B
) : RecyclerView.ViewHolder(binding.root)