package com.won983212.mongle.view.calendar

import android.view.View
import android.widget.TextView
import androidx.core.view.children
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder

class MongleMonthWeekHeaderBinder(
    private val daysOfWeek: Array<String>
) : MonthHeaderFooterBinder<MonthWeekdayContainer> {

    override fun create(view: View) = MonthWeekdayContainer(view)

    override fun bind(container: MonthWeekdayContainer, month: CalendarMonth) {
        if (container.weekdayContainer.tag == null) {
            container.weekdayContainer.tag = month.yearMonth
            container.weekdayContainer.children.map { it as TextView }
                .forEachIndexed { index, tv ->
                    tv.text = daysOfWeek[index]
                }
        }
    }
}