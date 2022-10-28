package com.rtsoju.mongle.presentation.view.statistics.range

import java.time.LocalDate
import java.time.YearMonth

open class MonthRange(year: Int, val month: Int) : YearRange(year) {
    init {
        if (month !in 1..12) {
            throw java.lang.IllegalArgumentException("Argument month is out of range. ($month is not in 1..12)")
        }
    }

    override fun next(): DateRange {
        val next = YearMonth.of(year, month).plusMonths(1)
        return MonthRange(next.year, next.monthValue)
    }

    override fun prev(): DateRange {
        val next = YearMonth.of(year, month).minusMonths(1)
        return MonthRange(next.year, next.monthValue)
    }

    override fun getRangeStart(): LocalDate {
        return toYearMonth().atDay(1)
    }

    override fun getRangeEnd(): LocalDate {
        return toYearMonth().atEndOfMonth()
    }

    override fun toString(): String {
        return "${year}년 ${month}월"
    }

    fun toYearMonth(): YearMonth {
        return YearMonth.of(year, month)
    }

    companion object {
        fun fromLocalDate(date: LocalDate): MonthRange {
            return MonthRange(date.year, date.monthValue)
        }
    }
}