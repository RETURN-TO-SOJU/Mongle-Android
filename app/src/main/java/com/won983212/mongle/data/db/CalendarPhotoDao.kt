package com.won983212.mongle.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.won983212.mongle.data.source.local.entity.EmotionalSentenceEntity
import com.won983212.mongle.data.source.local.entity.PhotoEntity
import com.won983212.mongle.domain.model.Emotion
import java.time.LocalDate

@Dao
interface CalendarPhotoDao {
    @Insert
    suspend fun insertPhotos(photos: List<PhotoEntity>)

    @Query("DELETE FROM photoentity WHERE date = :date")
    suspend fun deleteByDate(date: LocalDate)
}