package com.won983212.mongle.data.repository

import com.won983212.mongle.common.Emotion
import com.won983212.mongle.data.remote.api.RequestLifecycleCallback
import com.won983212.mongle.data.remote.model.MessageResult
import com.won983212.mongle.data.remote.model.response.CalendarDay
import com.won983212.mongle.data.remote.model.response.CalendarDayDetail
import com.won983212.mongle.data.remote.model.response.EmotionalSentence
import com.won983212.mongle.data.remote.source.CalendarDataSource
import com.won983212.mongle.repository.CalendarRepository
import java.time.LocalDate

internal class CalendarRepositoryImpl(
    private val calendarDataSource: CalendarDataSource
) : CalendarRepository {
    override suspend fun updateDiary(
        callback: RequestLifecycleCallback,
        date: LocalDate,
        text: String
    ): MessageResult? =
        calendarDataSource.updateDiary(callback, date, text)

    override suspend fun getCalendarDayMetadata(
        callback: RequestLifecycleCallback,
        startMonth: LocalDate,
        endMonth: LocalDate
    ): List<CalendarDay>? =
        calendarDataSource.getCalendarDayMetadata(callback, startMonth, endMonth)

    override suspend fun getCalendarDayDetail(
        callback: RequestLifecycleCallback,
        date: LocalDate
    ): CalendarDayDetail? =
        calendarDataSource.getCalendarDayDetail(callback, date)

    override suspend fun getDayEmotionalSentences(
        callback: RequestLifecycleCallback,
        date: LocalDate,
        emotion: Emotion
    ): List<EmotionalSentence>? =
        calendarDataSource.getDayEmotionalSentences(callback, date, emotion)
}