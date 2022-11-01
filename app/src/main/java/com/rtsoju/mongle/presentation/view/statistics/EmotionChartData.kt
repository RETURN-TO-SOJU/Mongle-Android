package com.rtsoju.mongle.presentation.view.statistics

import com.rtsoju.mongle.domain.model.Emotion
import kotlin.math.roundToInt

data class EmotionChartData(
    val emotion: Emotion,
    val count: Int,
    val proportion: Float
) {
    /** Emotion의 label id (string) */
    fun getLabelRes(): Int {
        return emotion.labelRes
    }

    /** Emotion의 color id (color#int) */
    fun getColorRes(): Int {
        return emotion.colorRes
    }

    /** EmotionChartData의 값을 요약하여 표현. 개수와 비율을 모두 포함해서 보여준다. */
    override fun toString(): String {
        val countText = if (count >= 1000000) {
            "${(count / 10000f).roundToInt() / 100f}M"
        } else if (count >= 1000) {
            "${(count / 10f).roundToInt() / 100f}K"
        } else {
            "${count}개"
        }
        return "%s (%.2f%%)".format(countText, proportion)
    }
}