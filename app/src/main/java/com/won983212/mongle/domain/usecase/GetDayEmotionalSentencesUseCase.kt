package com.won983212.mongle.domain.usecase

import com.won983212.mongle.data.model.Emotion
import com.won983212.mongle.data.source.remote.model.response.EmotionalSentence
import com.won983212.mongle.data.util.CachePolicy
import com.won983212.mongle.domain.repository.CalendarRepository
import java.time.LocalDate
import javax.inject.Inject

class GetDayEmotionalSentencesUseCase @Inject constructor(
    private val calendarRepository: CalendarRepository
) {
    suspend operator fun invoke(
        date: LocalDate,
        emotion: Emotion,
        cachePolicy: CachePolicy = CachePolicy.DEFAULT
    ): Result<List<EmotionalSentence>> {
        return calendarRepository.getDayEmotionalSentences(date, emotion, cachePolicy)
    }
}