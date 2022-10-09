package com.won983212.mongle.domain.usecase.calendar

import com.won983212.mongle.domain.model.CachePolicy
import com.won983212.mongle.domain.model.CalendarDayDetail
import com.won983212.mongle.domain.repository.CalendarRepository
import java.time.LocalDate
import javax.inject.Inject

/**
 * path variable로 필요한 년도, 월, 일(22/07/26)을 입력 받아 해당 일의 필요한 데이터를 조회한다.
 */
class GetCalendarDayDetailUseCase @Inject constructor(
    private val calendarRepository: CalendarRepository
) {
    suspend operator fun invoke(
        date: LocalDate,
        cachePolicy: CachePolicy = CachePolicy.DEFAULT
    ): Result<CalendarDayDetail> {
        return calendarRepository.getCalendarDayDetail(date, cachePolicy)
    }
}