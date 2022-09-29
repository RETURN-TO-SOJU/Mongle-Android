package com.won983212.mongle.presentation.util

import android.content.Context
import android.util.TypedValue

fun dpToPxFloat(context: Context?, dp: Float): Float {
    if (context == null) {
        return 0f
    }
    val dm = context.resources.displayMetrics
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, dm)
}

fun dpToPxInt(context: Context?, dp: Int): Int {
    return dpToPxFloat(context, dp.toFloat()).toInt()
}