package com.won983212.mongle.data.db.dao

import androidx.room.*
import com.won983212.mongle.data.db.entity.CalendarDayEntity
import com.won983212.mongle.data.db.entity.relation.CalendarDayWithDetails
import com.won983212.mongle.domain.model.CalendarDayPreview
import com.won983212.mongle.domain.model.Emotion
import java.time.LocalDate

@Dao
interface CalendarDao {

    @Transaction
    @Query("SELECT * FROM calendardayentity WHERE date = :date")
    suspend fun getCalendarDay(
        date: LocalDate
    ): CalendarDayWithDetails?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCalendarDay(calendarDayEntity: CalendarDayEntity)

    @Query(
        "SELECT date, emotion, keywords FROM calendardayentity " +
                "WHERE date >= :fromEpochDay AND date <= :toEpochDay " +
                "ORDER BY date"
    )
    suspend fun getCalendarDayPreview(
        fromEpochDay: Long,
        toEpochDay: Long
    ): List<CalendarDayPreview>

    @Query(
        "UPDATE calendardayentity " +
                "SET emotion = :emotion, keywords = :keywords " +
                "WHERE date = :date"
    )
    suspend fun updateCalendarDayPreview(
        date: LocalDate,
        emotion: Emotion?,
        keywords: List<String>
    ): Int

    @Query(
        "UPDATE calendardayentity " +
                "SET emotion = :emotion, " +
                "diary = :diary, " +
                "diaryFeedback = :diaryFeedback, " +
                "emotionProportions = :emotionProportions " +
                "WHERE date = :date"
    )
    suspend fun updateCalendarDayDetail(
        date: LocalDate,
        emotion: Emotion?,
        diary: String,
        diaryFeedback: String,
        emotionProportions: Map<String, Int>
    ): Int


    @Query("UPDATE calendardayentity SET diary = :diary WHERE date = :date")
    suspend fun updateDiary(
        date: LocalDate,
        diary: String
    ): Int

    @Query("UPDATE calendardayentity SET emotion = :emotion WHERE date = :date")
    suspend fun updateEmotion(
        date: LocalDate,
        emotion: Emotion
    ): Int
}