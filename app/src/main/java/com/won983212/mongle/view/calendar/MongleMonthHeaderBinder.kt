package com.won983212.mongle.view.calendar

import android.view.View
import android.widget.TextView
import androidx.core.view.children
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder

class MongleMonthHeaderBinder(val daysOfWeek: Array<String>) :
    MonthHeaderFooterBinder<MonthViewContainer> {

    override fun create(view: View) = MonthViewContainer(view)

    override fun bind(container: MonthViewContainer, month: CalendarMonth) {
        if (container.legendLayout.tag == null) {
            container.legendLayout.tag = month.yearMonth
            container.legendLayout.children.map { it as TextView }
                .forEachIndexed { index, tv ->
                    tv.text = daysOfWeek[index]
                }
        }
    }
}