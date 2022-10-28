package com.rtsoju.mongle.data.source.remote.dto.response

import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class StatisticsResponse<ScoreType>(
    @SerializedName("data")
    val data: Data<ScoreType>
) {
    data class Data<ScoreType>(
        @SerializedName("startDate")
        val startDate: LocalDate,
        @SerializedName("scoreList")
        val scoreList: List<ScoreType>,
        @SerializedName("happy")
        val happy: Int,
        @SerializedName("neutral")
        val neutral: Int,
        @SerializedName("angry")
        val angry: Int,
        @SerializedName("anxious")
        val anxious: Int,
        @SerializedName("tired")
        val tired: Int,
        @SerializedName("sad")
        val sad: Int
    )

    data class DateScore(
        @SerializedName("score")
        val score: Float,
        @SerializedName("date")
        val date: LocalDate
    )
}