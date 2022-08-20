package com.won983212.mongle.data.remote.model.response

import com.google.gson.annotations.SerializedName
import com.won983212.mongle.common.Emotion
import java.time.LocalDate

data class CalendarDay(
    @SerializedName("date")
    val date: LocalDate,
    @SerializedName("emotion")
    val emotion: Emotion,
    @SerializedName("subjectList")
    val subjectList: List<String>
)