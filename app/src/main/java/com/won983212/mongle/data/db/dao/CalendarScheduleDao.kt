package com.won983212.mongle.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.won983212.mongle.data.db.entity.ScheduleEntity
import java.time.LocalDate

@Dao
interface CalendarScheduleDao {
    @Insert
    suspend fun insertSchedules(schedules: List<ScheduleEntity>)

    @Query("DELETE FROM scheduleentity WHERE date = :date")
    suspend fun deleteByDate(date: LocalDate)
}