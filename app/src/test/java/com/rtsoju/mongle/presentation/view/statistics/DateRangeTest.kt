package com.rtsoju.mongle.presentation.view.statistics

import com.google.common.truth.Truth
import com.rtsoju.mongle.presentation.view.statistics.range.MonthRange
import com.rtsoju.mongle.presentation.view.statistics.range.WeekRange
import com.rtsoju.mongle.presentation.view.statistics.range.YearRange
import org.junit.Assert
import org.junit.Test
import java.time.LocalDate
import java.time.YearMonth

internal class DateRangeTest {
    @Test
    fun constructors() {
        Assert.assertThrows(IllegalArgumentException::class.java) {
            MonthRange(2022, 0)
        }
        Assert.assertThrows(IllegalArgumentException::class.java) {
            MonthRange(2022, 13)
        }
        MonthRange(2022, 9)
        Assert.assertThrows(IllegalArgumentException::class.java) {
            WeekRange(2022, 9, 0)
        }
        Assert.assertThrows(IllegalArgumentException::class.java) {
            WeekRange(2022, 9, 6)
        }
        WeekRange(2022, 9, 5)
    }

    @Test
    fun week_fromLocalDate() {
        Truth.assertThat(WeekRange.fromLocalDate(LocalDate.of(2022, 7, 31)).toString())
            .isEqualTo("2022년 7월 4주")
        Truth.assertThat(WeekRange.fromLocalDate(LocalDate.of(2022, 8, 1)).toString())
            .isEqualTo("2022년 8월 1주")
        Truth.assertThat(WeekRange.fromLocalDate(LocalDate.of(2022, 8, 29)).toString())
            .isEqualTo("2022년 9월 1주")
        Truth.assertThat(WeekRange.fromLocalDate(LocalDate.of(2022, 9, 1)).toString())
            .isEqualTo("2022년 9월 1주")
        Truth.assertThat(WeekRange.fromLocalDate(LocalDate.of(2022, 9, 5)).toString())
            .isEqualTo("2022년 9월 2주")
        Truth.assertThat(WeekRange.fromLocalDate(LocalDate.of(2022, 9, 26)).toString())
            .isEqualTo("2022년 9월 5주")
        Truth.assertThat(WeekRange.fromLocalDate(LocalDate.of(2022, 10, 2)).toString())
            .isEqualTo("2022년 9월 5주")
        Truth.assertThat(WeekRange.fromLocalDate(LocalDate.of(2022, 10, 3)).toString())
            .isEqualTo("2022년 10월 1주")
        Truth.assertThat(WeekRange.fromLocalDate(LocalDate.of(2022, 1, 1)).toString())
            .isEqualTo("2021년 12월 5주")
        Truth.assertThat(WeekRange.fromLocalDate(LocalDate.of(2022, 1, 3)).toString())
            .isEqualTo("2022년 1월 1주")
    }

    @Test
    fun year_next_prev() {
        Truth.assertThat(YearRange(2022).next().toString()).isEqualTo("2023년")
        Truth.assertThat(YearRange(2022).prev().toString()).isEqualTo("2021년")
    }

    @Test
    fun month_next_prev() {
        Truth.assertThat(MonthRange(2022, 9).next().toString()).isEqualTo("2022년 10월")
        Truth.assertThat(MonthRange(2022, 9).prev().toString()).isEqualTo("2022년 8월")

        Truth.assertThat(MonthRange(2022, 12).next().toString()).isEqualTo("2023년 1월")
        Truth.assertThat(MonthRange(2022, 1).prev().toString()).isEqualTo("2021년 12월")
    }

    @Test
    fun week_next_prev() {
        Truth.assertThat(WeekRange(2022, 9, 5).next().toString()).isEqualTo("2022년 10월 1주")
        Truth.assertThat(WeekRange(2022, 9, 1).prev().toString()).isEqualTo("2022년 8월 4주")
        Truth.assertThat(WeekRange(2022, 8, 4).next().toString()).isEqualTo("2022년 9월 1주")

        Truth.assertThat(WeekRange(2022, 9, 3).next().toString()).isEqualTo("2022년 9월 4주")
        Truth.assertThat(WeekRange(2022, 12, 5).next().toString()).isEqualTo("2023년 1월 1주")
        Truth.assertThat(WeekRange(2022, 1, 1).prev().toString()).isEqualTo("2021년 12월 5주")
    }

    @Test
    fun total_weeks() {
        Truth.assertThat(WeekRange.getCountOfWeeks(YearMonth.of(2022, 1))).isEqualTo(4)
        Truth.assertThat(WeekRange.getCountOfWeeks(YearMonth.of(2022, 2))).isEqualTo(4)
        Truth.assertThat(WeekRange.getCountOfWeeks(YearMonth.of(2022, 3))).isEqualTo(5)
        Truth.assertThat(WeekRange.getCountOfWeeks(YearMonth.of(2022, 4))).isEqualTo(4)
        Truth.assertThat(WeekRange.getCountOfWeeks(YearMonth.of(2022, 5))).isEqualTo(4)
        Truth.assertThat(WeekRange.getCountOfWeeks(YearMonth.of(2022, 6))).isEqualTo(5)
        Truth.assertThat(WeekRange.getCountOfWeeks(YearMonth.of(2022, 7))).isEqualTo(4)
        Truth.assertThat(WeekRange.getCountOfWeeks(YearMonth.of(2022, 8))).isEqualTo(4)
        Truth.assertThat(WeekRange.getCountOfWeeks(YearMonth.of(2022, 9))).isEqualTo(5)
        Truth.assertThat(WeekRange.getCountOfWeeks(YearMonth.of(2022, 10))).isEqualTo(4)
        Truth.assertThat(WeekRange.getCountOfWeeks(YearMonth.of(2022, 11))).isEqualTo(4)
        Truth.assertThat(WeekRange.getCountOfWeeks(YearMonth.of(2022, 12))).isEqualTo(5)
    }

    @Test
    fun week_start_end() {
        WeekRange(2022, 9, 1).run {
            Truth.assertThat(getRangeStart()).isEqualTo(LocalDate.of(2022, 8, 29))
            Truth.assertThat(getRangeEnd()).isEqualTo(LocalDate.of(2022, 9, 4))
        }
        WeekRange(2022, 9, 2).run {
            Truth.assertThat(getRangeStart()).isEqualTo(LocalDate.of(2022, 9, 5))
            Truth.assertThat(getRangeEnd()).isEqualTo(LocalDate.of(2022, 9, 11))
        }
    }
}