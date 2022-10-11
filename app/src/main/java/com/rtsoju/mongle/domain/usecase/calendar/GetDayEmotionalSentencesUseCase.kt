package com.rtsoju.mongle.domain.usecase.calendar

import com.rtsoju.mongle.domain.model.CachePolicy
import com.rtsoju.mongle.domain.model.Emotion
import com.rtsoju.mongle.domain.model.EmotionalSentence
import com.rtsoju.mongle.domain.repository.CalendarRepository
import java.time.LocalDate
import javax.inject.Inject

/**
 * 특정 일의 분석화면에서 감정이를 눌렀을 때 해당하는 대화 문장들을 불러온다.
 */
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