package com.won983212.mongle.presentation.common.calendar

import android.view.View
import com.kizitonwose.calendarview.ui.ViewContainer
import com.won983212.mongle.databinding.CalendarWeekdayHeaderBinding

internal class MonthWeekdayContainer(view: View) : ViewContainer(view) {
    val weekdayContainer = CalendarWeekdayHeaderBinding.bind(view).root
}