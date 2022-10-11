package com.rtsoju.mongle.domain.usecase.calendar

import com.rtsoju.mongle.data.source.remote.dto.MessageResult
import com.rtsoju.mongle.domain.model.Emotion
import com.rtsoju.mongle.domain.repository.CalendarRepository
import java.time.LocalDate
import javax.inject.Inject

/**
 * path variable로 필요한 년도, 월, 일(22/07/26)을 입력 받은 후 body로 그날의 감정을 설정한다.
 * @param date 일기 작성일
 * @param emotion 그날의 감정
 */
class UpdateEmotionUseCase @Inject constructor(
    private val calendarRepository: CalendarRepository
) {
    suspend operator fun invoke(
        date: LocalDate,
        emotion: Emotion
    ): Result<MessageResult> {
        return calendarRepository.updateEmotion(date, emotion)
    }
}