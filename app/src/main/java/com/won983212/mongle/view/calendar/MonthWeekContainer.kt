package com.won983212.mongle.view.calendar

import android.view.View
import com.kizitonwose.calendarview.ui.ViewContainer
import com.won983212.mongle.databinding.CalendarMonthHeaderBinding

class MonthWeekContainer(view: View) : ViewContainer(view) {
    val weekViewContainer = CalendarMonthHeaderBinding.bind(view).root
}