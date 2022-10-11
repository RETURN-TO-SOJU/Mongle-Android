package com.rtsoju.mongle.presentation.view.daydetail.adapter

import com.rtsoju.mongle.R
import com.rtsoju.mongle.databinding.ListitemAnalyzedEmotionBinding
import com.rtsoju.mongle.domain.model.Emotion
import com.rtsoju.mongle.presentation.base.BaseRecyclerAdapter
import com.rtsoju.mongle.presentation.view.daydetail.model.AnalyzedEmotionPresentationModel

class AnalyzedEmotionListAdapter(
    private val clickListener: OnEmotionClick
) : BaseRecyclerAdapter<ListitemAnalyzedEmotionBinding, AnalyzedEmotionPresentationModel>() {

    override val itemLayoutId: Int = R.layout.listitem_analyzed_emotion

    override fun bind(
        binding: ListitemAnalyzedEmotionBinding,
        item: AnalyzedEmotionPresentationModel
    ) {
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