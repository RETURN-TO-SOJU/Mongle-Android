package com.won983212.mongle.data.db

import androidx.room.*
import com.won983212.mongle.data.source.local.entity.EmotionProportionEntity
import com.won983212.mongle.data.source.local.entity.EmotionalSentenceEntity
import com.won983212.mongle.data.source.local.entity.PhotoEntity
import com.won983212.mongle.domain.model.Emotion
import com.won983212.mongle.domain.model.EmotionProportion
import java.time.LocalDate

@Dao
interface CalendarEmotionProportionDao {
    @Insert
    suspend fun insertEmotionProportions(values: List<EmotionProportionEntity>)

    @Query("DELETE FROM emotionproportionentity WHERE date = :date")
    suspend fun deleteByDate(date: LocalDate)
}