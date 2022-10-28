package com.rtsoju.mongle.presentation.view.statistics

import com.kizitonwose.calendarview.utils.yearMonth
import java.time.LocalDate
import java.time.Period
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
sealed class DateRange {
    open class Year(val year: Int) : DateRange() {
        override fun toString(): String {
            return "${year}년"
        }

        companion object {
            fun fromLocalDate(date: LocalDate): Year {
                return Year(date.year)
            }
        }
    }

    open class Month(year: Int, val month: Int) : Year(year) {
        init {
            if (month !in 1..12) {
                throw java.lang.IllegalArgumentException("Argument month is out of range. ($month is not in 1..12)")
            }
        }

        override fun toString(): String {
            return "${year}년 ${month}월"
        }

        companion object {
            fun fromLocalDate(date: LocalDate): Month {
                return Month(date.year, date.monthValue)
            }
        }
    }

    class Week(year: Int, month: Int, val week: Int) : Month(year, month) {
        init {
            val weeks =
                YearMonth.of(year, month).atEndOfMonth().get(WeekFields.ISO.weekOfMonth())
            if (week !in 1..weeks) {
                throw java.lang.IllegalArgumentException("Argument week is out of range. ($week is not in 1..$weeks)")
            }
        }

        override fun toString(): String {
            return "${year}년 ${month}월 ${week}주"
        }

        companion object {
            private fun getWeekStartDay(yearMonth: YearMonth): LocalDate {
                val firstDay = yearMonth.atDay(1)
                val weekDay = firstDay.dayOfWeek.value
                return if (weekDay > 4) {
                    firstDay.plusDays((8 - weekDay).toLong())
                } else {
                    firstDay.minusDays((weekDay - 1).toLong())
                }
            }

            fun fromLocalDate(date: LocalDate): Week {
                val startDay = getWeekStartDay(date.yearMonth)
                val nextMonthStartDay = getWeekStartDay(date.yearMonth.plusMonths(1))

                if (date < startDay) {
                    val prevMonth = date.yearMonth.minusMonths(1)
                    val prevMonthStartDay = getWeekStartDay(prevMonth)
                    val week = Period.between(prevMonthStartDay, date).days / 7 + 1
                    return Week(prevMonth.year, prevMonth.monthValue, week)
                } else if (date >= nextMonthStartDay) {
                    val nextMonth = date.yearMonth.plusMonths(1)
                    val nextMonthStartDay = getWeekStartDay(nextMonth)
                    val week = Period.between(nextMonthStartDay, date).days / 7 + 1
                    return Week(nextMonth.year, nextMonth.monthValue, week)
                } else {
                    val week = Period.between(startDay, date).days / 7 + 1
                    return Week(date.year, date.monthValue, week)
                }
            }
        }
    }
}