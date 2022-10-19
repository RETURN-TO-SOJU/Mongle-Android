package com.rtsoju.mongle.presentation.view.messages

import com.rtsoju.mongle.R
import com.rtsoju.mongle.databinding.ListitemMessageBinding
import com.rtsoju.mongle.presentation.base.BaseRecyclerAdapter

class EmotionMessageListAdapter :
    BaseRecyclerAdapter<ListitemMessageBinding, EmotionMessage>() {

    override val itemLayoutId: Int = R.layout.listitem_message

    override fun bind(binding: ListitemMessageBinding, item: EmotionMessage) {
        binding.imageListitemMessageEmotion.setImageResource(item.emotion.iconRes)
        binding.textListitemMessage.text = item.message
    }
}