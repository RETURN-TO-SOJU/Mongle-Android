package com.rtsoju.mongle.domain.model

import java.time.LocalDate

data class CalendarDayPreview(
    val date: LocalDate,
    val emotion: Emotion?,
    val keywords: List<String>
)