package com.won983212.mongle.data.source.local

import com.won983212.mongle.data.model.Emotion
import com.won983212.mongle.data.source.CalendarDataSource
import com.won983212.mongle.data.source.api.RequestLifecycleCallback
import com.won983212.mongle.data.source.remote.model.MessageResult
import com.won983212.mongle.data.source.remote.model.response.CalendarDay
import com.won983212.mongle.data.source.remote.model.response.CalendarDayDetail
import com.won983212.mongle.data.source.remote.model.response.DiaryFeedback
import com.won983212.mongle.data.source.remote.model.response.EmotionalSentence
import java.time.LocalDate
import javax.inject.Inject

internal class MemoryCalendarDataSource @Inject constructor(
) : CalendarDataSource {

    override suspend fun updateDiary(
        callback: RequestLifecycleCallback,
        accessToken: String,
        date: LocalDate,
        text: String
    ): MessageResult? {
        TODO("Not yet implemented")
    }

    override suspend fun getCalendarDayMetadata(
        callback: RequestLifecycleCallback,
        accessToken: String,
        startMonth: LocalDate,
        endMonth: LocalDate
    ): List<CalendarDay>? {
        TODO("Not yet implemented")
    }

    override suspend fun getCalendarDayDetail(
        callback: RequestLifecycleCallback,
        accessToken: String,
        date: LocalDate
    ): CalendarDayDetail? {
        TODO("Not yet implemented")
    }

    override suspend fun getDayEmotionalSentences(
        callback: RequestLifecycleCallback,
        accessToken: String,
        date: LocalDate,
        emotion: Emotion
    ): List<EmotionalSentence>? {
        TODO("Not yet implemented")
    }

    override suspend fun getDiaryFeedback(
        callback: RequestLifecycleCallback,
        diaryText: String
    ): DiaryFeedback? {
        TODO("Not yet implemented")
    }
}