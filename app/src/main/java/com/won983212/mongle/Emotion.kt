package com.won983212.mongle

import android.content.res.Resources
import androidx.annotation.DrawableRes

enum class Emotion(val label: String, @DrawableRes val iconRes: Int) {
    HAPPY("happy", R.drawable.emotion_happy),
    NEUTRAL("neutral", R.drawable.emotion_neutral),
    ANGRY("angry", R.drawable.emotion_angry),
    ANXIOUS("anxious", R.drawable.emotion_anxious),
    EMBARRASS("embarrass", R.drawable.emotion_embarrass),
    SAD("sad", R.drawable.emotion_sad);

    companion object {
        fun of(label: String): Emotion {
            val keyword = label.lowercase()
            values().forEach {
                if (it.label == keyword) {
                    return it
                }
            }
            throw Resources.NotFoundException(label)
        }
    }
}