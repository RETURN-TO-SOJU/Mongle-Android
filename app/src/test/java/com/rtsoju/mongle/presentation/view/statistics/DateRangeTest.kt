package com.rtsoju.mongle.presentation.view.statistics

import com.google.common.truth.Truth
import org.junit.Assert
import org.junit.Test
import java.time.LocalDate

internal class DateRangeTest {
    @Test
    fun of() {
        Assert.assertThrows(IllegalArgumentException::class.java) {
            DateRange.of(2022, 0)
        }
        Assert.assertThrows(IllegalArgumentException::class.java) {
            DateRange.of(2022, 13)
        }
        DateRange.of(2022, 9)
        Assert.assertThrows(IllegalArgumentException::class.java) {
            DateRange.of(2022, 9, 0)
        }
        Assert.assertThrows(IllegalArgumentException::class.java) {
            DateRange.of(2022, 9, 6)
        }
        DateRange.of(2022, 9, 5)
    }

    @Test
    fun fromLocalDate() {
        Truth.assertThat(DateRange.fromLocalDate(LocalDate.of(2022, 8, 29)).toString())
            .isEqualTo("2022년 9월 1주")
        Truth.assertThat(DateRange.fromLocalDate(LocalDate.of(2022, 9, 1)).toString())
            .isEqualTo("2022년 9월 1주")
        Truth.assertThat(DateRange.fromLocalDate(LocalDate.of(2022, 9, 5)).toString())
            .isEqualTo("2022년 9월 2주")
        Truth.assertThat(DateRange.fromLocalDate(LocalDate.of(2022, 10, 1)).toString())
            .isEqualTo("2022년 9월 5주")
    }
}