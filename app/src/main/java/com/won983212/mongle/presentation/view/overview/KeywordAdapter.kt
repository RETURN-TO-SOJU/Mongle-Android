package com.won983212.mongle.presentation.view.overview

import com.won983212.mongle.R
import com.won983212.mongle.databinding.ListitemKeywordBinding
import com.won983212.mongle.presentation.base.BaseRecyclerAdapter

class KeywordAdapter : BaseRecyclerAdapter<ListitemKeywordBinding, String>() {

    override val itemLayoutId: Int = R.layout.listitem_keyword

    override fun bind(binding: ListitemKeywordBinding, item: String) {
        val resources = binding.root.context.resources
        binding.apply {
            textListitemKeyword.text = resources.getString(R.string.keyword_template, item)
        }
    }
}