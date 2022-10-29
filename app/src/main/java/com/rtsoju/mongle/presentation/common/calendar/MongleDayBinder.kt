package com.rtsoju.mongle.presentation.common.calendar

import android.util.Log
import android.view.View
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import java.time.LocalDate

internal class MongleDayBinder(
    private val calendar: MongleCalendar,
    private val dayClickListener: (LocalDate) -> Unit
) :
    DayBinder<DayViewContainer> {

    private val today = LocalDate.now()

    override fun create(view: View): DayViewContainer = DayViewContainer(view, dayClickListener)

    override fun bind(container: DayViewContainer, day: CalendarDay) {
        container.initialize(day)
        if (day.owner == DayOwner.THIS_MONTH) {
            if (day.date == today) { // Point color dot
                container.setToday()
            }
            val emotion = calendar.dayEmotions?.get(day.date)
            if (emotion != null) {
                container.setEmotion(emotion)
            }
            if (day.date == calendar.selectedDate) { // Point color rectangle
                container.setSelected()
            }
        } else { // disabled text
            container.setDisabled()
        }
    }
}