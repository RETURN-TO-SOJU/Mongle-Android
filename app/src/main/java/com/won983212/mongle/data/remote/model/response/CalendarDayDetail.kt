package com.won983212.mongle.data.remote.model.response

import com.google.gson.annotations.SerializedName
import com.won983212.mongle.data.model.Emotion
import java.time.LocalDateTime

data class CalendarDayDetail(
    @SerializedName("imageList")
    val imageList: List<Photo>,
    @SerializedName("diary")
    val diary: String,
    @SerializedName("scheduleList")
    val scheduleList: List<Schedule>,
    @SerializedName("emotionList")
    val emotionList: List<EmotionProportion>,
    @SerializedName("emotion")
    val emotion: Emotion?
) {
    data class Schedule(
        @SerializedName("name")
        val name: String,
        @SerializedName("calendar")
        val calendar: String,
        @SerializedName("startTime")
        val startTime: LocalDateTime,
        @SerializedName("endTime")
        val endTime: LocalDateTime
    )

    data class EmotionProportion(
        @SerializedName("emotion")
        val emotion: Emotion,
        @SerializedName("percent")
        val percent: Int
    )

    data class Photo(
        @SerializedName("url")
        val url: String,
        @SerializedName("time")
        val time: LocalDateTime
    )
}