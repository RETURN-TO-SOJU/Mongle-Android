package com.won983212.mongle.domain.model

data class CalendarDayDetail(
    val imageList: List<Photo>,
    val diary: String?,
    val diaryFeedback: String,
    val scheduleList: List<Schedule>,
    val emotionList: List<EmotionProportion>,
    val emotion: Emotion?
)