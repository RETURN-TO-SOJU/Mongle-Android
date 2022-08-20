package com.won983212.mongle.view.daydetail.model

import com.won983212.mongle.common.Emotion
import com.won983212.mongle.common.base.BaseRecyclerItem

data class AnalyzedEmotion(
    val emotion: Emotion,
    val proportion: Int
) : BaseRecyclerItem