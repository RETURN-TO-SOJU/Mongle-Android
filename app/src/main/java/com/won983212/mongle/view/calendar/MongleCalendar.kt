package com.won983212.mongle.view.calendar

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.view.children
import com.kizitonwose.calendarview.CalendarView
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
import com.won983212.mongle.R
import com.won983212.mongle.databinding.CalendarMongleBinding
import com.won983212.mongle.util.dpToPx
import java.time.YearMonth
import java.time.temporal.WeekFields
import java.util.*

class MongleCalendar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    init {
        val binding = CalendarMongleBinding.inflate(LayoutInflater.from(context))
        val currentMonth = YearMonth.now()
        val firstMonth = currentMonth.minusMonths(5)
        val lastMonth = currentMonth.plusMonths(5)
        val firstDayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek
        val daysOfWeek = resources.getStringArray(R.array.calendar_weekdays)

        binding.calendarView.apply {
            daySize = CalendarView.sizeAutoWidth(dpToPx(context, 44))

            dayBinder = object : DayBinder<DayViewContainer> {
                override fun create(view: View): DayViewContainer = DayViewContainer(view)
                override fun bind(container: DayViewContainer, day: CalendarDay) {
                    container.textView.text = day.date.dayOfMonth.toString()
                }
            }

            monthHeaderBinder = object : MonthHeaderFooterBinder<MonthViewContainer> {
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

            setupAsync(firstMonth, lastMonth, firstDayOfWeek) {
                this.scrollToMonth(currentMonth)
            }
        }

        addView(binding.root)
    }
}