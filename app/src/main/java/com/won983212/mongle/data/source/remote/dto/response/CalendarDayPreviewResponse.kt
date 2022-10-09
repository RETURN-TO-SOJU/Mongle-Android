package com.won983212.mongle.data.source.remote.dto.response

import com.google.gson.annotations.SerializedName
import com.won983212.mongle.domain.model.Emotion
import java.time.LocalDate

data class CalendarDayPreviewResponse(
    @SerializedName("date")
    val date: LocalDate,
    @SerializedName("emotion")
    val emotion: Emotion?,
    @SerializedName("subjectList")
    val subjectList: List<String>
)