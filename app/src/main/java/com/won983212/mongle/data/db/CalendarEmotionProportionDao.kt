package com.won983212.mongle.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.won983212.mongle.data.source.local.entity.EmotionProportionEntity
import java.time.LocalDate

@Dao
interface CalendarEmotionProportionDao {
    @Insert
    suspend fun insertEmotionProportions(values: List<EmotionProportionEntity>)

    @Query("DELETE FROM emotionproportionentity WHERE date = :date")
    suspend fun deleteByDate(date: LocalDate)
}