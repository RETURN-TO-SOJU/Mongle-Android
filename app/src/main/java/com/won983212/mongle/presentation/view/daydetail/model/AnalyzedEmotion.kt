package com.won983212.mongle.presentation.view.daydetail.model

import com.won983212.mongle.data.model.Emotion
import com.won983212.mongle.presentation.base.BaseRecyclerItem

data class AnalyzedEmotion(
    val emotion: Emotion,
    val proportion: Int
) : BaseRecyclerItem