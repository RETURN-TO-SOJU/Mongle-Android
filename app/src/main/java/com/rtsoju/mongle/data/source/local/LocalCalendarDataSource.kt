package com.rtsoju.mongle.data.source.local

import androidx.room.withTransaction
import com.rtsoju.mongle.data.db.AppDatabase
import com.rtsoju.mongle.data.db.entity.CalendarDayEntity
import com.rtsoju.mongle.data.mapper.toCalendarDayEntity
import com.rtsoju.mongle.data.mapper.toDomainModel
import com.rtsoju.mongle.data.mapper.toEntity
import com.rtsoju.mongle.domain.model.*
import com.rtsoju.mongle.exception.NoResultException
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

internal class LocalCalendarDataSource
@Inject constructor(private val db: AppDatabase) {

    private val calendarDao = db.calenderDao()
    private val sentencesDao = db.emotionalSentencesDao()
    private val photoDao = db.calendarPhotoDao()
    private val scheduleDao = db.calenderScheduleDao()

    suspend fun updateDiary(
        date: LocalDate,
        text: String
    ) {
        db.withTransaction {
            val updated = calendarDao.updateDiary(date, text)
            if (updated == 0) {
                calendarDao.insertCalendarDay(
                    CalendarDayEntity(
                        date,
                        null,
                        listOf(),
                        text,
                        "",
                        EmotionProportion.defaultProportionMap()
                    )
                )
            }
        }
    }

    suspend fun updateEmotion(
        date: LocalDate,
        emotion: Emotion
    ) {
        db.withTransaction {
            val updated = calendarDao.updateEmotion(date, emotion)
            if (updated == 0) {
                calendarDao.insertCalendarDay(
                    CalendarDayEntity(
                        date,
                        emotion,
                        listOf(),
                        "",
                        "",
                        EmotionProportion.defaultProportionMap()
                    )
                )
            }
        }
    }

    suspend fun getCalendarDayPreview(
        startMonth: YearMonth,
        endMonth: YearMonth
    ): Result<List<CalendarDayPreview>> {
        val startEpoch = startMonth.atDay(1).toEpochDay()
        val endEpoch = endMonth.atEndOfMonth().toEpochDay()
        val result = calendarDao.getCalendarDayPreview(startEpoch, endEpoch)
        return if (result.isNotEmpty()) {
            Result.success(result)
        } else {
            Result.failure(NoResultException())
        }
    }

    suspend fun updateCalendarDayPreview(
        startMonth: YearMonth,
        endMonth: YearMonth,
        days: List<CalendarDayPreview>
    ) {
        db.withTransaction {
            val start = startMonth.atDay(1).toEpochDay()
            val end = endMonth.atEndOfMonth().toEpochDay()
            calendarDao.deleteCalendarDayRange(start, end)

            days.forEach {
                val updated =
                    calendarDao.updateCalendarDayPreview(it.date, it.emotion, it.keywords)
                if (updated == 0) {
                    calendarDao.insertCalendarDay(it.toCalendarDayEntity())
                }
            }
        }
    }

    suspend fun getCalendarDayDetail(date: LocalDate): Result<CalendarDayDetail> {
        val result = calendarDao.getCalendarDay(date)
        return if (result != null) {
            Result.success(
                CalendarDayDetail(
                    result.day.date,
                    result.photos.map { it.toDomainModel() },
                    result.day.diary,
                    result.day.diaryFeedback,
                    result.schedules.map { it.toDomainModel() },
                    result.day.emotionProportions.toDomainModel(),
                    result.day.emotion
                )
            )
        } else {
            Result.failure(NoResultException())
        }
    }

    suspend fun updateCalendarDayDetail(detail: CalendarDayDetail) {
        db.withTransaction {
            val updated = calendarDao.updateCalendarDayDetail(
                detail.date,
                detail.emotion,
                detail.diary,
                detail.diaryFeedback,
                detail.emotionList.toEntity()
            )
            if (updated == 0) {
                calendarDao.insertCalendarDay(detail.toCalendarDayEntity())
            }
            photoDao.deleteByDate(detail.date)
            photoDao.insertPhotos(detail.imageList.map { it.toEntity(detail.date) })
            scheduleDao.deleteByDate(detail.date)
            scheduleDao.insertSchedules(detail.scheduleList.map { it.toEntity(detail.date) })
        }
    }

    suspend fun getDayEmotionalSentences(
        date: LocalDate,
        emotion: Emotion
    ): Result<List<EmotionalSentence>> {
        val result = sentencesDao.getSentences(date, emotion)
        return if (result.isNotEmpty()) {
            Result.success(result.map { it.toDomainModel() })
        } else {
            Result.failure(NoResultException())
        }
    }

    suspend fun updateDayEmotionalSentences(
        date: LocalDate,
        emotion: Emotion,
        sentences: List<EmotionalSentence>
    ) {
        db.withTransaction {
            sentencesDao.deleteSentences(date, emotion)
            sentencesDao.insertSentences(sentences.map { it.toEntity(date) })
        }
    }
}