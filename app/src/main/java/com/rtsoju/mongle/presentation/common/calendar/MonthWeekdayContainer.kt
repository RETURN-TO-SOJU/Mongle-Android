package com.rtsoju.mongle.presentation.common.calendar

import android.view.View
import com.kizitonwose.calendarview.ui.ViewContainer
import com.rtsoju.mongle.databinding.CalendarWeekdayHeaderBinding

internal class MonthWeekdayContainer(view: View) : ViewContainer(view) {
    val weekdayContainer = CalendarWeekdayHeaderBinding.bind(view).root
}