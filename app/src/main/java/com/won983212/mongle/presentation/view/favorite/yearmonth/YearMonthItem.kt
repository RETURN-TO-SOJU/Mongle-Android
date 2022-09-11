package com.won983212.mongle.presentation.view.favorite.yearmonth

import com.won983212.mongle.DatetimeFormats
import java.time.YearMonth

data class YearMonthItem(
    val date: YearMonth,
    val index: Int,
    val selected: Boolean
) {
    fun getFormattedDate(): String =
        date.format(DatetimeFormats.MONTH_DOT)

    fun ofSelected(selected: Boolean): YearMonthItem {
        return if (this.selected != selected) {
            YearMonthItem(date, index, selected)
        } else {
            this
        }
    }
}