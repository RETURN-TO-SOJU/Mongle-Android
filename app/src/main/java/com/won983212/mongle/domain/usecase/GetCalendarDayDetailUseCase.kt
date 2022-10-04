package com.won983212.mongle.domain.usecase

import com.won983212.mongle.data.source.remote.model.response.CalendarDayDetail
import com.won983212.mongle.data.util.CachePolicy
import com.won983212.mongle.domain.repository.CalendarRepository
import java.time.LocalDate
import javax.inject.Inject

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