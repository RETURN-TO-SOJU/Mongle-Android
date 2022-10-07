package com.won983212.mongle.data.source.local

import com.won983212.mongle.data.db.AppDatabase
import com.won983212.mongle.data.util.toDomainModel
import com.won983212.mongle.domain.model.CalendarDayDetail
import com.won983212.mongle.domain.model.CalendarDayPreview
import com.won983212.mongle.domain.model.Emotion
import com.won983212.mongle.domain.model.EmotionalSentence
import com.won983212.mongle.exception.NoResultException
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

internal class LocalCalendarDataSource @Inject constructor(
    private val db: AppDatabase
) {

    suspend fun updateDiary(
        date: LocalDate,
        text: String
    ) {
        db.calenderDao().updateDiary(date, text)
    }

    suspend fun updateEmotion(
        date: LocalDate,
        emotion: Emotion
    ) {
        db.calenderDao().updateEmotion(date, emotion)
    }

    suspend fun getCalendarDayPreview(
        startMonth: YearMonth,
        endMonth: YearMonth
    ): Result<List<CalendarDayPreview>> {
        val startEpoch = startMonth.atDay(1).toEpochDay()
        val endEpoch = endMonth.atEndOfMonth().toEpochDay()
        val result = db.calenderDao().getCalendarDayPreview(startEpoch, endEpoch)
        return if (result.isNotEmpty()) {
            Result.success(result)
        } else {
            Result.failure(NoResultException())
        }
    }

    suspend fun updateCalendarDayPreview(
        days: List<CalendarDayPreview>
    ) {
        db.calenderDao().cacheCalendarDayPreview(days)
    }

    suspend fun getCalendarDayDetail(
        date: LocalDate
    ): Result<CalendarDayDetail> {
        val result = db.calenderDao().getCalendarDayDetails(date)
        return if (result != null) {
            Result.success(
                CalendarDayDetail(
                    result.photos.map { it.toDomainModel() },
                    result.day.diary,
                    result.day.diaryFeedback,
                    result.schedules.map { it.toDomainModel() },
                    result.emotionProportions.map { it.toDomainModel() },
                    result.day.emotion
                )
            )
        } else {
            Result.failure(NoResultException())
        }
    }

    suspend fun updateCalendarDayDetail(
        date: LocalDate,
        detail: CalendarDayDetail
    ) {

    }

    suspend fun getDayEmotionalSentences(
        date: LocalDate,
        emotion: Emotion
    ): Result<List<EmotionalSentence>> {
        val result = db.calenderDao().getDayEmotionalSentences(date, emotion)
        return if (result.isNotEmpty()) {
            Result.success(result.map { it.toDomainModel() })
        } else {
            Result.failure(NoResultException())
        }
    }

    suspend fun updateDayEmotionalSentences(
        date: LocalDate,
        sentences: List<EmotionalSentence>
    ) {

    }
}