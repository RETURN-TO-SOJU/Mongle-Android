package com.won983212.mongle.domain.usecase.calendar

import com.won983212.mongle.data.util.CachePolicy
import com.won983212.mongle.domain.model.CalendarDayPreview
import com.won983212.mongle.domain.repository.CalendarRepository
import java.time.YearMonth
import javax.inject.Inject

/**
 * query parameter로 필요한 년도 및 월을 받아 해당 월의 감정 및 주제들을 반환한다.
 * ex) /api/calender/{year}?start=3&end=5 : 3월부터 5월까지 데이터
 * @param startMonth 시작 월. 이 매개변수의 '일'은 무시됨
 * @param endMonth 끝 월. 이 달까지 포함해서 결과가 반환된다. 이 매개변수의 '일'은 무시됨
 */
class GetCalendarDayMetadataUseCase @Inject constructor(
    private val calendarRepository: CalendarRepository
) {
    suspend operator fun invoke(
        startMonth: YearMonth,
        endMonth: YearMonth,
        cachePolicy: CachePolicy = CachePolicy.DEFAULT
    ): Result<List<CalendarDayPreview>> {
        return calendarRepository.getCalendarDayPreview(startMonth, endMonth, cachePolicy)
    }
}