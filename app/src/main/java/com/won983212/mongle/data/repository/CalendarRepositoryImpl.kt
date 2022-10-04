package com.won983212.mongle.data.repository

import com.won983212.mongle.data.model.Emotion
import com.won983212.mongle.data.source.remote.RemoteCalendarDataSource
import com.won983212.mongle.data.source.remote.model.MessageResult
import com.won983212.mongle.data.source.remote.model.response.CalendarDay
import com.won983212.mongle.data.source.remote.model.response.CalendarDayDetail
import com.won983212.mongle.data.source.remote.model.response.EmotionalSentence
import com.won983212.mongle.data.util.CachePolicy
import com.won983212.mongle.domain.repository.CalendarRepository
import java.time.LocalDate
import java.time.YearMonth

internal class CalendarRepositoryImpl(
    private val remoteCalendarDataSource: RemoteCalendarDataSource
) : CalendarRepository {

    override suspend fun updateDiary(date: LocalDate, text: String): Result<MessageResult> =
        remoteCalendarDataSource.updateDiary(
            date,
            text
        )

    override suspend fun updateEmotion(date: LocalDate, emotion: Emotion): Result<MessageResult> =
        remoteCalendarDataSource.updateEmotion(date, emotion)

    // TODO Cache Policy 구현
    override suspend fun getCalendarDayMetadata(
        startMonth: YearMonth,
        endMonth: YearMonth,
        cachePolicy: CachePolicy
    ): Result<List<CalendarDay>> =
        remoteCalendarDataSource.getCalendarDayMetadata(
            startMonth,
            endMonth
        )

    override suspend fun getCalendarDayDetail(
        date: LocalDate,
        cachePolicy: CachePolicy
    ): Result<CalendarDayDetail> =
        remoteCalendarDataSource.getCalendarDayDetail(date)

    override suspend fun getDayEmotionalSentences(
        date: LocalDate,
        emotion: Emotion,
        cachePolicy: CachePolicy
    ): Result<List<EmotionalSentence>> =
        remoteCalendarDataSource.getDayEmotionalSentences(
            date,
            emotion
        )
}