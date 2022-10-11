package com.rtsoju.mongle.presentation.base

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

open class BaseRecyclerViewHolder<B : ViewDataBinding>(
    val binding: B
) : RecyclerView.ViewHolder(binding.root)