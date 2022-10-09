package com.won983212.mongle.domain.model

data class EmotionProportion(
    val emotion: Emotion,
    val percent: Int
) {
    companion object {
        fun defaultProportionMap(): Map<String, Int> {
            return Emotion.values().associate {
                it.name to 0
            }
        }
    }
}