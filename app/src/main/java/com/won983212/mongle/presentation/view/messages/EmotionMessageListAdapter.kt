package com.won983212.mongle.presentation.view.messages

import com.won983212.mongle.R
import com.won983212.mongle.databinding.ListitemMessageBinding
import com.won983212.mongle.presentation.base.BaseRecyclerAdapter

class EmotionMessageListAdapter :
    BaseRecyclerAdapter<ListitemMessageBinding, EmotionMessage>() {

    override val itemLayoutId: Int = R.layout.listitem_message

    override fun bind(binding: ListitemMessageBinding, item: EmotionMessage) {
        binding.imageListitemMessageEmotion.setImageResource(item.emotion.iconRes)
        binding.textListitemMessage.text = item.message
    }
}