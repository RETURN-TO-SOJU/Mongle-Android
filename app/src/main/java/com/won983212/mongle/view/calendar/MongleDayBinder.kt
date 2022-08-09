package com.won983212.mongle.view.calendar

import android.view.View
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import com.won983212.mongle.R
import com.won983212.mongle.util.setTextColorRes
import java.time.LocalDate

class MongleDayBinder(
    private val calendar: MongleCalendar,
    private val dayClickListener: (LocalDate) -> Unit
) :
    DayBinder<DayViewContainer> {

    private val today = LocalDate.now()

    override fun create(view: View): DayViewContainer = DayViewContainer(view, dayClickListener)

    override fun bind(container: DayViewContainer, day: CalendarDay) {
        val dayTextView = container.textView

        container.day = day
        container.todayIndicator.visibility = View.GONE
        container.background.visibility = View.INVISIBLE
        dayTextView.text = day.date.dayOfMonth.toString()
        dayTextView.background = null

        if (day.owner == DayOwner.THIS_MONTH) {
            if (day.date == today) { // Point color dot
                dayTextView.setTextColorRes(R.color.text)
                container.todayIndicator.visibility = View.VISIBLE
            }
            if (day.date == calendar.selectedDate) { // Point color rectangle
                dayTextView.setTextColorRes(R.color.on_point)
                container.background.visibility = View.VISIBLE
            }
            if (day.date != today && day.date != calendar.selectedDate) { // plain text
                dayTextView.setTextColorRes(R.color.text)
            }
        } else { // disabled text
            dayTextView.setTextColorRes(R.color.disabled)
        }
    }
}