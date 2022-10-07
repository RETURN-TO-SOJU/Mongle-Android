package com.won983212.mongle.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.won983212.mongle.data.source.local.model.Favorite

@Dao
interface CalendarDao {
    @Query(
        "SELECT * FROM calendarday"
    )
    suspend fun getCalendarDayPreview(): List<Favorite>

    @Query(
        "SELECT * FROM favorite " +
                "WHERE date >= :fromEpochDay AND date <= :toEpochDay " +
                "ORDER BY date"
    )
    /** Ordered by date */
    suspend fun getRange(fromEpochDay: Long, toEpochDay: Long): List<Favorite>

    @Insert
    suspend fun insert(favorite: Favorite)

    @Query("DELETE FROM favorite WHERE id=:userId")
    suspend fun deleteById(userId: Int)
}