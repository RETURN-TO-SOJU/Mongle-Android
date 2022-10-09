package com.won983212.mongle.domain.model

import java.time.LocalDate

data class CalendarDayPreview(
    val date: LocalDate,
    val emotion: Emotion?,
    val keywords: List<String>
)