package com.won983212.mongle.data.source.remote.dto.response

import com.google.gson.annotations.SerializedName
import com.won983212.mongle.domain.model.*
import java.time.LocalDateTime

data class CalendarDayDetailResponse(
    @SerializedName("imageList")
    val imageList: List<PhotoResponse>,
    @SerializedName("diary")
    val diary: String?,
    @SerializedName("diaryFeedback")
    val diaryFeedback: String,
    @SerializedName("scheduleList")
    val scheduleList: List<ScheduleResponse>,
    @SerializedName("emotionList")
    val emotionList: List<EmotionProportionResponse>,
    @SerializedName("emotion")
    val emotion: Emotion?
) {
    data class ScheduleResponse(
        @SerializedName("name")
        val name: String,
        @SerializedName("calendar")
        val calendar: String,
        @SerializedName("startTime")
        val startTime: LocalDateTime,
        @SerializedName("endTime")
        val endTime: LocalDateTime
    )

    data class EmotionProportionResponse(
        @SerializedName("emotion")
        val emotion: Emotion,
        @SerializedName("percent")
        val percent: Int
    )

    data class PhotoResponse(
        @SerializedName("url")
        val url: String,
        @SerializedName("time")
        val time: LocalDateTime
    )
}