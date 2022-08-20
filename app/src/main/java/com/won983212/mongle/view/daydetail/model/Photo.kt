package com.won983212.mongle.view.daydetail.model

import android.graphics.drawable.Drawable
import com.won983212.mongle.common.base.BaseRecyclerItem

data class Photo(
    val image: Drawable?, // TODO (DEBUG) must be not null later
    val timeText: String
): BaseRecyclerItem