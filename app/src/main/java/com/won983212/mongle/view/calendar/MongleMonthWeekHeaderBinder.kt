package com.won983212.mongle.view.calendar

import android.view.View
import android.widget.TextView
import androidx.core.view.children
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder

class MongleMonthWeekHeaderBinder(
    private val daysOfWeek: Array<String>
) : MonthHeaderFooterBinder<MonthWeekContainer> {

    override fun create(view: View) = MonthWeekContainer(view)

    override fun bind(container: MonthWeekContainer, month: CalendarMonth) {
        if (container.weekViewContainer.tag == null) {
            container.weekViewContainer.tag = month.yearMonth
            container.weekViewContainer.children.map { it as TextView }
                .forEachIndexed { index, tv ->
                    tv.text = daysOfWeek[index]
                }
        }
    }
}