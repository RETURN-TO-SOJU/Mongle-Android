package com.rtsoju.mongle.domain.model

import java.time.LocalDate

data class CalendarDayDetail(
    val date: LocalDate,
    val imageList: List<Photo>,
    val diary: String,
    val diaryFeedback: String,
    val scheduleList: List<Schedule>,
    val emotionList: List<EmotionProportion>,
    val emotion: Emotion?
)