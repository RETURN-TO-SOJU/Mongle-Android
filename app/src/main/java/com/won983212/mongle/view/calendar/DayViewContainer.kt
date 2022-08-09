package com.won983212.mongle.view.calendar

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.ViewContainer
import com.won983212.mongle.R
import java.time.LocalDate

class DayViewContainer(view: View, dayClickListener: (LocalDate) -> Unit) :
    ViewContainer(view) {

    lateinit var day: CalendarDay
    val textView = view.findViewById<TextView>(R.id.calendarDayText)
    val background = view.findViewById<ImageView>(R.id.daySelectedBackground)
    val todayIndicator = view.findViewById<ImageView>(R.id.dotIsToday)

    init {
        view.setOnClickListener {
            if (day.owner == DayOwner.THIS_MONTH) {
                dayClickListener(day.date)
            }
        }
    }
}