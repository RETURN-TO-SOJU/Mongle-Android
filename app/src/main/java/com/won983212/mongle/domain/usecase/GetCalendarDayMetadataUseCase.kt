package com.won983212.mongle.domain.usecase

import com.won983212.mongle.data.source.remote.model.response.CalendarDay
import com.won983212.mongle.data.util.CachePolicy
import com.won983212.mongle.domain.repository.CalendarRepository
import java.time.YearMonth
import javax.inject.Inject

class GetCalendarDayMetadataUseCase @Inject constructor(
    private val calendarRepository: CalendarRepository
) {
    suspend operator fun invoke(
        startMonth: YearMonth,
        endMonth: YearMonth,
        cachePolicy: CachePolicy = CachePolicy.DEFAULT
    ): Result<List<CalendarDay>> {
        return calendarRepository.getCalendarDayMetadata(startMonth, endMonth, cachePolicy)
    }
}