package com.won983212.mongle.util

import android.content.Context
import android.util.TypedValue

fun dpToPx(context: Context, dp: Int): Int {
    val dm = context.resources.displayMetrics
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), dm).toInt()
}