package com.won983212.mongle.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.won983212.mongle.data.db.entity.EmotionalSentenceEntity
import com.won983212.mongle.domain.model.Emotion
import java.time.LocalDate

@Dao
interface EmotionalSentenceDao {
    @Query("SELECT * FROM emotionalsentenceentity WHERE date = :date AND emotion = :emotion")
    suspend fun getSentences(
        date: LocalDate,
        emotion: Emotion
    ): List<EmotionalSentenceEntity>

    @Query("DELETE FROM emotionalsentenceentity WHERE date = :date AND emotion = :emotion")
    suspend fun deleteSentences(
        date: LocalDate,
        emotion: Emotion
    )

    @Insert
    suspend fun insertSentences(sentences: List<EmotionalSentenceEntity>)
}