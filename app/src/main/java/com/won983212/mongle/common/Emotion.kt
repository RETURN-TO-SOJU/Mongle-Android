package com.won983212.mongle.common

import android.content.res.Resources
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.won983212.mongle.R

enum class Emotion(val id: String, @StringRes val label: Int, @DrawableRes val iconRes: Int) {
    HAPPY("happy", R.string.emotion_happy, R.drawable.emotion_happy),
    NEUTRAL("neutral", R.string.emotion_neutral, R.drawable.emotion_neutral),
    ANGRY("angry", R.string.emotion_angry, R.drawable.emotion_angry),
    ANXIOUS("anxious", R.string.emotion_anxious, R.drawable.emotion_anxious),
    TIRED("tired", R.string.emotion_tired, R.drawable.emotion_tired),
    SAD("sad", R.string.emotion_sad, R.drawable.emotion_sad);

    companion object {
        fun of(id: String): Emotion {
            val keyword = id.lowercase()
            values().forEach {
                if (it.id == keyword) {
                    return it
                }
            }
            throw Resources.NotFoundException(id)
        }
    }
}