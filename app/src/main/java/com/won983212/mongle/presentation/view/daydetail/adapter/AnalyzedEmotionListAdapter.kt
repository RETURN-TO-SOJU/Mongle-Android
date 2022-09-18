package com.won983212.mongle.presentation.view.daydetail.adapter

import com.won983212.mongle.R
import com.won983212.mongle.data.model.Emotion
import com.won983212.mongle.databinding.ListitemAnalyzedEmotionBinding
import com.won983212.mongle.presentation.base.BaseRecyclerAdapter
import com.won983212.mongle.presentation.view.daydetail.model.AnalyzedEmotion

class AnalyzedEmotionListAdapter(
    private val clickListener: OnEmotionClick
) : BaseRecyclerAdapter<ListitemAnalyzedEmotionBinding, AnalyzedEmotion>() {

    override val itemLayoutId: Int = R.layout.listitem_analyzed_emotion

    override fun bind(binding: ListitemAnalyzedEmotionBinding, item: AnalyzedEmotion) {
        val text = binding.textListitemAnalyzedProportion.context.resources
            .getString(R.string.percent_template, item.proportion.toString())
        binding.root.layoutParams.width = parent.measuredWidth / list.size
        binding.textListitemAnalyzedProportion.text = text
        binding.imageListitemAnalyzedEmotion.apply {
            setImageResource(item.emotion.bigIconRes)
            setOnClickListener {
                clickListener.onClick(item.emotion)
            }
        }
    }

    fun interface OnEmotionClick {
        fun onClick(emotion: Emotion)
    }
}