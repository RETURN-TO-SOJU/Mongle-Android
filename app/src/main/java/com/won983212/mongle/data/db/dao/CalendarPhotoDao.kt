package com.won983212.mongle.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.won983212.mongle.data.db.entity.PhotoEntity
import java.time.LocalDate

@Dao
interface CalendarPhotoDao {
    @Insert
    suspend fun insertPhotos(photos: List<PhotoEntity>)

    @Query("DELETE FROM photoentity WHERE date = :date")
    suspend fun deleteByDate(date: LocalDate)
}