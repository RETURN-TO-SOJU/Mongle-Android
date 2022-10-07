package com.won983212.mongle.data.db

import androidx.room.*
import com.won983212.mongle.data.source.local.entity.CalendarDayEntity
import com.won983212.mongle.data.source.local.entity.EmotionalSentenceEntity
import com.won983212.mongle.data.source.local.entity.relation.CalendarDayWithDetails
import com.won983212.mongle.domain.model.CalendarDayPreview
import com.won983212.mongle.domain.model.Emotion
import java.time.LocalDate

@Dao
interface CalendarDao {
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
                "SET emotion = :emotion AND keywords = :keywords " +
                "WHERE date = :date"
    )
    suspend fun updateCalendarDayPreview(
        date: LocalDate,
        emotion: Emotion,
        keywords: List<String>
    ): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCalendarDayPreview(calendarDayEntity: CalendarDayEntity)

    @Transaction
    suspend fun cacheCalendarDayPreview(
        values: List<CalendarDayPreview>
    ) {
        values.forEach {
            val updated = updateCalendarDayPreview(it.date, it.emotion, it.keywords)
            if (updated == 0) {
                insertCalendarDayPreview(
                    CalendarDayEntity(
                        0,
                        it.date,
                        it.emotion,
                        it.keywords,
                        "",
                        ""
                    )
                )
            }
        }
    }

    @Query("SELECT * FROM calendardayentity WHERE date = :date")
    suspend fun getCalendarDayDetails(
        date: LocalDate
    ): CalendarDayWithDetails?

    @Query("SELECT * FROM emotionalsentenceentity WHERE date = :date AND emotion = :emotion")
    suspend fun getDayEmotionalSentences(
        date: LocalDate,
        emotion: Emotion
    ): List<EmotionalSentenceEntity>

    @Query("UPDATE calendardayentity SET diary = :diary WHERE date = :date")
    suspend fun updateDiary(
        date: LocalDate,
        diary: String
    )

    @Query("UPDATE calendardayentity SET emotion = :emotion WHERE date = :date")
    suspend fun updateEmotion(
        date: LocalDate,
        emotion: Emotion
    )
}