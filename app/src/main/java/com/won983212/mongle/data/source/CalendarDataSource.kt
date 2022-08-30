package com.won983212.mongle.data.source

import com.won983212.mongle.data.model.Emotion
import com.won983212.mongle.data.source.api.RequestLifecycleCallback
import com.won983212.mongle.data.source.remote.model.MessageResult
import com.won983212.mongle.data.source.remote.model.response.CalendarDay
import com.won983212.mongle.data.source.remote.model.response.CalendarDayDetail
import com.won983212.mongle.data.source.remote.model.response.DiaryFeedback
import com.won983212.mongle.data.source.remote.model.response.EmotionalSentence
import java.time.LocalDate

// TODO "Data Source에서 token을 가져가는 방식" 개선 필요
interface CalendarDataSource {
    suspend fun updateDiary(
        callback: RequestLifecycleCallback,
        accessToken: String,
        date: LocalDate,
        text: String
    ): MessageResult?

    suspend fun getCalendarDayMetadata(
        callback: RequestLifecycleCallback,
        accessToken: String,
        startMonth: LocalDate,
        endMonth: LocalDate
    ): List<CalendarDay>?

    suspend fun getCalendarDayDetail(
        callback: RequestLifecycleCallback,
        accessToken: String,
        date: LocalDate
    ): CalendarDayDetail?

    suspend fun getDayEmotionalSentences(
        callback: RequestLifecycleCallback,
        accessToken: String,
        date: LocalDate,
        emotion: Emotion
    ): List<EmotionalSentence>?

    suspend fun getDiaryFeedback(
        callback: RequestLifecycleCallback,
        diaryText: String
    ): DiaryFeedback?
}