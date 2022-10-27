package com.rtsoju.mongle.presentation.view.statistics

import com.kizitonwose.calendarview.utils.yearMonth
import java.time.LocalDate
import java.time.YearMonth
import java.time.temporal.WeekFields
import java.util.*

/**
 * 날짜 범위를 나타낸다. year만 명시하면 그 연도 전체(year.01.01 ~ year.12.31)을 나타낸다.
 * month, week 순으로 추가 명시하면 더 짧은 범위를 나타낼 수 있다. (month: 월, week: 주 단위 범위)
 * @param year 년. 연도만 명시하면 그 연도의 모든 일들을 포함한 날짜를 표현한다.
 * @param month 월. 년+월을 명시하면 해당 월의 모든 일들을 포함한 날짜를 표현한다. 범위는 1~12
 * @param week 주. 년+월+주를 명시하면 해당 주의 모든 일들을 포함한 날짜를 표현한다. 범위는 1~5이지만, 월에 따라서 다르다.
 */
class DateRange private constructor(
    private val year: Int,
    private val month: Int = 0,
    private val week: Int = 0
) {

    private fun zeroConditionalText(input: String, value: Int): String {
        if (value == 0) {
            return ""
        }
        return input
    }

    override fun toString(): String {
        val yearText = "${year}년"
        val monthText = zeroConditionalText(" ${month}월", month)
        val weekText = zeroConditionalText(" ${week}주", week)
        return "${yearText}${monthText}${weekText}"
    }

    companion object {
        fun of(year: Int, month: Int? = null, week: Int? = null): DateRange {
            if (month != null && month !in 1..12) {
                throw java.lang.IllegalArgumentException("Argument month is out of range. ($month is not in 1..12)")
            }

            if (month != null && week != null) {
                val weeks =
                    YearMonth.of(year, month).atEndOfMonth().get(WeekFields.ISO.weekOfMonth())
                if (week !in 1..weeks) {
                    throw java.lang.IllegalArgumentException("Argument week is out of range. ($week is not in 1..$weeks)")
                }
            }

            return DateRange(year, month ?: 0, week ?: 0)
        }

        fun fromLocalDate(date: LocalDate): DateRange {
            var yearMonth = date.yearMonth
            var week = date.get(WeekFields.ISO.weekOfMonth())

            if(week == 0){
                yearMonth = yearMonth.minusMonths(1)
                week = yearMonth.atEndOfMonth().get(WeekFields.ISO.weekOfMonth())
            }

            return DateRange(yearMonth.year, yearMonth.monthValue, week)
        }
    }
}