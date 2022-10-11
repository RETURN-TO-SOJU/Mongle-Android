package com.rtsoju.mongle.domain.model

import java.time.LocalDateTime

data class Schedule(
    val name: String,
    val calendar: String,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime
)