package com.rtsoju.mongle.presentation.view.statistics

import com.google.common.truth.Truth
import org.junit.Assert
import org.junit.Test
import java.time.LocalDate

internal class DateRangeTest {
    @Test
    fun constructors() {
        Assert.assertThrows(IllegalArgumentException::class.java) {
            DateRange.Month(2022, 0)
        }
        Assert.assertThrows(IllegalArgumentException::class.java) {
            DateRange.Month(2022, 13)
        }
        DateRange.Month(2022, 9)
        Assert.assertThrows(IllegalArgumentException::class.java) {
            DateRange.Week(2022, 9, 0)
        }
        Assert.assertThrows(IllegalArgumentException::class.java) {
            DateRange.Week(2022, 9, 6)
        }
        DateRange.Week(2022, 9, 5)
    }

    @Test
    fun week_fromLocalDate() {
        Truth.assertThat(DateRange.Week.fromLocalDate(LocalDate.of(2022, 8, 29)).toString())
            .isEqualTo("2022년 9월 1주")
        Truth.assertThat(DateRange.Week.fromLocalDate(LocalDate.of(2022, 9, 1)).toString())
            .isEqualTo("2022년 9월 1주")
        Truth.assertThat(DateRange.Week.fromLocalDate(LocalDate.of(2022, 9, 5)).toString())
            .isEqualTo("2022년 9월 2주")
        Truth.assertThat(DateRange.Week.fromLocalDate(LocalDate.of(2022, 9, 26)).toString())
            .isEqualTo("2022년 9월 5주")
        Truth.assertThat(DateRange.Week.fromLocalDate(LocalDate.of(2022, 10, 2)).toString())
            .isEqualTo("2022년 9월 5주")
        Truth.assertThat(DateRange.Week.fromLocalDate(LocalDate.of(2022, 10, 3)).toString())
            .isEqualTo("2022년 10월 1주")
        Truth.assertThat(DateRange.Week.fromLocalDate(LocalDate.of(2022, 1, 1)).toString())
            .isEqualTo("2022년 12월 5주")
        Truth.assertThat(DateRange.Week.fromLocalDate(LocalDate.of(2022, 1, 3)).toString())
            .isEqualTo("2022년 1월 1주")
    }
}