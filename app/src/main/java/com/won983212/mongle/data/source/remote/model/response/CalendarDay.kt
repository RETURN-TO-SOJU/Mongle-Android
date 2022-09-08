package com.won983212.mongle.data.source.remote.model.response

import com.google.gson.annotations.SerializedName
import com.won983212.mongle.data.model.Emotion
import java.time.LocalDate

data class CalendarDay(
    @SerializedName("date")
    val date: LocalDate,
    @SerializedName("emotion")
    val emotion: Emotion,
    @SerializedName("subjectList")
    val subjectList: List<String>
)