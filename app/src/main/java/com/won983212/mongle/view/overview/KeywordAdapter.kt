package com.won983212.mongle.view.overview

import com.won983212.mongle.R
import com.won983212.mongle.common.base.BaseRecyclerAdapter
import com.won983212.mongle.databinding.ListitemKeywordBinding

class KeywordAdapter : BaseRecyclerAdapter<ListitemKeywordBinding, Keyword>() {

    override val itemLayoutId: Int = R.layout.listitem_keyword

    override fun bind(binding: ListitemKeywordBinding, item: Keyword) {
        val resources = binding.root.context.resources
        binding.apply {
            textListitemKeyword.text = resources.getString(R.string.keyword_template, item.label)
        }
    }
}