package com.won983212.mongle.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.won983212.mongle.data.source.local.entity.EmotionalSentenceEntity
import com.won983212.mongle.data.source.local.entity.PhotoEntity
import com.won983212.mongle.data.source.local.entity.ScheduleEntity
import com.won983212.mongle.domain.model.Emotion
import com.won983212.mongle.domain.model.Schedule
import java.time.LocalDate

@Dao
interface CalendarScheduleDao {
    @Insert
    suspend fun insertSchedules(schedules: List<ScheduleEntity>)
    
    @Query("DELETE FROM scheduleentity WHERE date = :date")
    suspend fun deleteByDate(date: LocalDate)
}