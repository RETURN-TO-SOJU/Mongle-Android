package com.rtsoju.mongle.data.repository

import com.rtsoju.mongle.data.source.local.LocalCalendarDataSource
import com.rtsoju.mongle.data.source.remote.RemoteCalendarDataSource
import com.rtsoju.mongle.data.source.remote.dto.MessageResult
import com.rtsoju.mongle.domain.model.*
import com.rtsoju.mongle.domain.repository.CalendarRepository
import java.time.LocalDate
import java.time.YearMonth

internal class CalendarRepositoryImpl(
    private val localCalendarDataSource: LocalCalendarDataSource,
    private val remoteCalendarDataSource: RemoteCalendarDataSource
) : CalendarRepository {

    override suspend fun updateDiary(date: LocalDate, text: String): Result<MessageResult> {
        localCalendarDataSource.updateDiary(date, text)
        return remoteCalendarDataSource.updateDiary(date, text)
    }

    override suspend fun updateEmotion(date: LocalDate, emotion: Emotion): Result<MessageResult> {
        localCalendarDataSource.updateEmotion(date, emotion)
        return remoteCalendarDataSource.updateEmotion(date, emotion)
    }

    override suspend fun getCalendarDayPreview(
        startMonth: YearMonth,
        endMonth: YearMonth,
        cachePolicy: CachePolicy
    ): Result<List<CalendarDayPreview>> {
        return cachePolicy.get(object : CachePolicy.CacheableResource<List<CalendarDayPreview>> {
            override suspend fun loadFromCache(): Result<List<CalendarDayPreview>> {
                return localCalendarDataSource.getCalendarDayPreview(startMonth, endMonth)
            }

            override suspend fun saveCallResult(value: List<CalendarDayPreview>) {
                localCalendarDataSource.updateCalendarDayPreview(startMonth, endMonth, value)
            }

            override suspend fun fetch(): Result<List<CalendarDayPreview>> {
                return remoteCalendarDataSource.getCalendarDayMetadata(
                    startMonth,
                    endMonth
                )
            }

            override fun getResourceName(): String {
                return "CALENDAR_DAY_PREVIEW $startMonth $endMonth"
            }
        })
    }

    override suspend fun getCalendarDayDetail(
        date: LocalDate,
        cachePolicy: CachePolicy
    ): Result<CalendarDayDetail> {
        return cachePolicy.get(object : CachePolicy.CacheableResource<CalendarDayDetail> {
            override suspend fun loadFromCache(): Result<CalendarDayDetail> {
                return localCalendarDataSource.getCalendarDayDetail(date)
            }

            override suspend fun saveCallResult(value: CalendarDayDetail) {
                localCalendarDataSource.updateCalendarDayDetail(value)
            }

            override suspend fun fetch(): Result<CalendarDayDetail> {
                return remoteCalendarDataSource.getCalendarDayDetail(date)
            }

            override fun getResourceName(): String {
                return "CALENDAR_DAY_DETAIL $date"
            }
        })
    }

    override suspend fun getDayEmotionalSentences(
        date: LocalDate,
        emotion: Emotion,
        cachePolicy: CachePolicy
    ): Result<List<EmotionalSentence>> {
        return cachePolicy.get(object :
            CachePolicy.CacheableResource<List<EmotionalSentence>> {
            override suspend fun loadFromCache(): Result<List<EmotionalSentence>> {
                return localCalendarDataSource.getDayEmotionalSentences(date, emotion)
            }

            override suspend fun saveCallResult(value: List<EmotionalSentence>) {
                localCalendarDataSource.updateDayEmotionalSentences(date, emotion, value)
            }

            override suspend fun fetch(): Result<List<EmotionalSentence>> {
                return remoteCalendarDataSource.getDayEmotionalSentences(date, emotion)
            }

            override fun getResourceName(): String {
                return "DAY_EMOTIONAL_SENTENCES $date $emotion"
            }
        })
    }
}