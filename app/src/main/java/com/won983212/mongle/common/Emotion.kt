package com.won983212.mongle.common

import android.content.res.Resources
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.google.gson.annotations.SerializedName
import com.won983212.mongle.R

enum class Emotion(
    @StringRes val label: Int,
    @DrawableRes val iconRes: Int,
    @DrawableRes val bigIconRes: Int
) {
    @SerializedName("HAPPY")
    HAPPY(
        R.string.emotion_happy,
        R.drawable.emotion_happy,
        R.drawable.emotion_happy_big
    ),

    @SerializedName("NEUTRAL")
    NEUTRAL(
        R.string.emotion_neutral,
        R.drawable.emotion_neutral,
        R.drawable.emotion_neutral_big
    ),

    @SerializedName("ANGRY")
    ANGRY(
        R.string.emotion_angry,
        R.drawable.emotion_angry,
        R.drawable.emotion_angry_big
    ),

    @SerializedName("ANXIOUS")
    ANXIOUS(
        R.string.emotion_anxious,
        R.drawable.emotion_anxious,
        R.drawable.emotion_anxious_big
    ),

    @SerializedName("TIRED")
    TIRED(
        R.string.emotion_tired,
        R.drawable.emotion_tired,
        R.drawable.emotion_tired_big
    ),

    @SerializedName("SAD")
    SAD(
        R.string.emotion_sad,
        R.drawable.emotion_sad,
        R.drawable.emotion_sad_big
    );

    val id get() = this.name.lowercase()

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