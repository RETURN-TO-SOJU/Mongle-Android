package com.rtsoju.mongle.presentation.view.statistics.range

import java.time.LocalDate

open class YearRange(val year: Int) : DateRange {
    override fun next(): DateRange {
        return YearRange(year + 1)
    }

    override fun prev(): DateRange {
        return YearRange(year - 1)
    }

    override fun getRangeStart(): LocalDate {
        return LocalDate.of(year, 1, 1)
    }

    override fun getRangeEnd(): LocalDate {
        return LocalDate.of(year, 12, 31)
    }

    override fun toString(): String {
        return "${year}년"
    }

    companion object {
        fun fromLocalDate(date: LocalDate): YearRange {
            return YearRange(date.year)
        }
    }
}