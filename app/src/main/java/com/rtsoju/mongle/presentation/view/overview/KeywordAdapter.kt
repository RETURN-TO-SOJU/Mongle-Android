package com.rtsoju.mongle.presentation.view.overview

import com.rtsoju.mongle.R
import com.rtsoju.mongle.databinding.ListitemKeywordBinding
import com.rtsoju.mongle.presentation.base.BaseRecyclerAdapter

class KeywordAdapter : BaseRecyclerAdapter<ListitemKeywordBinding, String>() {

    override val itemLayoutId: Int = R.layout.listitem_keyword

    override fun bind(binding: ListitemKeywordBinding, item: String) {
        val resources = binding.root.context.resources
        binding.apply {
            textListitemKeyword.text = resources.getString(R.string.keyword_template, item)
        }
    }
}