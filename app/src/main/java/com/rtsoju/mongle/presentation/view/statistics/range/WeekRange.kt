package com.rtsoju.mongle.presentation.view.statistics.range

import com.kizitonwose.calendarview.utils.yearMonth
import java.time.LocalDate
import java.time.YearMonth
import java.time.temporal.ChronoUnit

class WeekRange(year: Int, month: Int, val week: Int) : MonthRange(year, month) {
    init {
        val weeks = getCountOfWeeks(YearMonth.of(year, month))
        if (week !in 1..weeks) {
            throw java.lang.IllegalArgumentException("Argument week is out of range. ($week is not in 1..$weeks)")
        }
    }

    override fun next(): DateRange {
        val yearMonth = toYearMonth()
        if (week >= getCountOfWeeks(yearMonth)) {
            val newYearMonth = yearMonth.plusMonths(1)
            return WeekRange(newYearMonth.year, newYearMonth.monthValue, 1)
        }
        return WeekRange(year, month, week + 1)
    }

    override fun prev(): DateRange {
        val yearMonth = toYearMonth()
        if (week <= 1) {
            val newYearMonth = yearMonth.minusMonths(1)
            return WeekRange(
                newYearMonth.year,
                newYearMonth.monthValue,
                getCountOfWeeks(newYearMonth)
            )
        }
        return WeekRange(year, month, week - 1)
    }

    override fun getRangeStart(): LocalDate {
        return getWeekStartDay(toYearMonth()).plusDays((7 * (week - 1)).toLong())
    }

    override fun getRangeEnd(): LocalDate {
        return getWeekStartDay(toYearMonth()).plusDays((7 * week - 1).toLong())
    }

    override fun toString(): String {
        return "${year}년 ${month}월 ${week}주"
    }

    companion object {
        fun getWeekStartDay(yearMonth: YearMonth): LocalDate {
            val firstDay = yearMonth.atDay(1)
            val weekDay = firstDay.dayOfWeek.value
            return if (weekDay > 4) {
                firstDay.plusDays((8 - weekDay).toLong())
            } else {
                firstDay.minusDays((weekDay - 1).toLong())
            }
        }

        fun getCountOfWeeks(yearMonth: YearMonth): Int {
            val startDay = getWeekStartDay(yearMonth)
            val nextMonthStartDay = getWeekStartDay(yearMonth.plusMonths(1)).minusDays(1)
            val week = ChronoUnit.DAYS.between(startDay, nextMonthStartDay) / 7 + 1
            return week.toInt()
        }

        fun fromLocalDate(date: LocalDate): WeekRange {
            var yearMonth = date.yearMonth
            var startDay = getWeekStartDay(yearMonth)

            val nextMonth = date.yearMonth.plusMonths(1)
            val nextMonthStartDay = getWeekStartDay(nextMonth)

            if (date < startDay) {
                yearMonth = date.yearMonth.minusMonths(1)
                startDay = getWeekStartDay(yearMonth)
            } else if (date >= nextMonthStartDay) {
                yearMonth = nextMonth
                startDay = nextMonthStartDay
            }

            val week = ChronoUnit.DAYS.between(startDay, date) / 7 + 1
            return WeekRange(yearMonth.year, yearMonth.monthValue, week.toInt())
        }
    }
}