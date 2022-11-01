package com.rtsoju.mongle.domain.usecase.calendar

import com.rtsoju.mongle.data.source.remote.dto.MessageResult
import com.rtsoju.mongle.domain.repository.CalendarRepository
import com.rtsoju.mongle.domain.repository.PasswordRepository
import com.rtsoju.mongle.exception.UnknownPasswordException
import java.time.LocalDate
import javax.inject.Inject

/**
 * path variable로 필요한 년도, 월, 일(22/07/26)을 입력 받은 후 body로 일기 내용을 입력받아 일기를 작성한다.
 * @param date 일기 작성일
 * @param text 일기 내용
 */
class UpdateDiaryUseCase @Inject constructor(
    private val calendarRepository: CalendarRepository,
    private val passwordRepository: PasswordRepository
) {
    suspend operator fun invoke(
        date: LocalDate,
        text: String
    ): Result<MessageResult> {
        val pwd = passwordRepository.getDataPassword() ?: return Result.failure(
            UnknownPasswordException()
        )
        return calendarRepository.updateDiary(date, text, pwd)
    }
}