package com.rtsoju.mongle.domain.usecase.calendar

import com.rtsoju.mongle.domain.model.CachePolicy
import com.rtsoju.mongle.domain.model.CalendarDayDetail
import com.rtsoju.mongle.domain.repository.CalendarRepository
import com.rtsoju.mongle.domain.repository.PasswordRepository
import java.time.LocalDate
import javax.inject.Inject

/**
 * path variable로 필요한 년도, 월, 일(22/07/26)을 입력 받아 해당 일의 필요한 데이터를 조회한다.
 */
class GetCalendarDayDetailUseCase @Inject constructor(
    private val calendarRepository: CalendarRepository,
    private val passwordRepository: PasswordRepository
) {
    suspend operator fun invoke(
        date: LocalDate,
        cachePolicy: CachePolicy = CachePolicy.DEFAULT
    ): Result<CalendarDayDetail> {
        return calendarRepository.getCalendarDayDetail(date, cachePolicy).map {
            it.copy(diary = passwordRepository.decryptByKeyPassword(it.diary))
        }
    }
}