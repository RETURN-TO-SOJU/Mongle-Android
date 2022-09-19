package com.won983212.mongle.presentation.common.calendar

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.kizitonwose.calendarview.CalendarView
import com.kizitonwose.calendarview.utils.next
import com.kizitonwose.calendarview.utils.previous
import com.kizitonwose.calendarview.utils.yearMonth
import com.won983212.mongle.R
import com.won983212.mongle.presentation.util.dpToPx
import com.won983212.mongle.data.model.Emotion
import com.won983212.mongle.databinding.CalendarMongleBinding
import com.won983212.mongle.presentation.base.event.OnSelectedListener
import com.won983212.mongle.util.DatetimeFormats
import java.time.LocalDate
import java.time.YearMonth
import java.time.temporal.WeekFields
import java.util.*

class MongleCalendar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val binding = CalendarMongleBinding.inflate(LayoutInflater.from(context))

    var selectedDate: LocalDate? = null
        private set

    var dayEmotions: Map<LocalDate, Emotion>? = null
        private set

    private var selectedListener: OnSelectedListener<LocalDate>? = null
    private var initializedListener: OnInitializedListener? = null

    init {
        val currentMonth = YearMonth.now()
        val firstDayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek
        val daysOfWeek = resources.getStringArray(R.array.calendar_weekdays)

        binding.calendar.apply {
            daySize = CalendarView.sizeAutoWidth(dpToPx(context, 44))
            dayBinder = MongleDayBinder(this@MongleCalendar, this@MongleCalendar::selectDate)
            monthHeaderBinder = MongleMonthWeekHeaderBinder(daysOfWeek)
            monthScrollListener = {
                binding.textCalendarMonth.text =
                    DatetimeFormats.MONTH_DOT_SPACE.format(it.yearMonth)
            }
            isNestedScrollingEnabled = false
            setupAsync(
                currentMonth.minusMonths(5),
                currentMonth.plusMonths(5),
                firstDayOfWeek
            ) {
                this.scrollToMonth(currentMonth)
                dayEmotions?.let { setDayEmotions(it) }
                initializedListener?.onInitialize()
            }
        }

        initEvents()
        addView(binding.root)
    }

    private fun initEvents() {
        binding.btnCalendarPrevMonth.setOnClickListener {
            binding.calendar.apply {
                findFirstVisibleMonth()?.let {
                    smoothScrollToMonth(it.yearMonth.previous)
                }
            }
        }

        binding.btnCalendarNextMonth.setOnClickListener {
            binding.calendar.apply {
                findFirstVisibleMonth()?.let {
                    smoothScrollToMonth(it.yearMonth.next)
                }
            }
        }
    }

    fun selectDate(date: LocalDate?) {
        if (selectedDate != date) {
            val oldDate = selectedDate
            selectedDate = date

            oldDate?.let { binding.calendar.notifyDateChanged(it) }
            date?.let {
                binding.calendar.scrollToMonth(date.yearMonth)
                binding.calendar.notifyDateChanged(date)
                selectedListener?.onSelected(date)
            }
        }
    }

    private fun findDiff(a: Map<LocalDate, Emotion>, b: Map<LocalDate, Emotion>): Set<LocalDate> {
        val result = mutableSetOf<LocalDate>()
        result.addAll(a.keys)
        for (ent in b) {
            val aValue = a[ent.key]
            if (aValue != null) {
                if (aValue == ent.value) {
                    result.remove(ent.key)
                }
            } else {
                result.add(ent.key)
            }
        }
        return result
    }

    fun setDayEmotions(emotionMapping: Map<LocalDate, Emotion>) {
        val oldEmotions = dayEmotions
        dayEmotions = emotionMapping

        if (binding.calendar.adapter != null) {
            val updates: Set<LocalDate> = if (oldEmotions != null) {
                findDiff(oldEmotions, emotionMapping)
            } else {
                emotionMapping.keys
            }

            for (day in updates) {
                binding.calendar.notifyDateChanged(day)
            }
        }
    }

    fun setDayEmotion(date: LocalDate, emotion: Emotion) {
        if (dayEmotions != null) {
            dayEmotions?.toMutableMap()?.set(date, emotion)
            if (binding.calendar.adapter != null) {
                binding.calendar.notifyDateChanged(date)
            }
        } else {
            setDayEmotions(mapOf(date to emotion))
        }
    }

    fun setOnSelectedListener(listener: OnSelectedListener<LocalDate>) {
        selectedListener = listener
    }

    fun setOnInitializedListener(listener: OnInitializedListener) {
        initializedListener = listener
    }

    fun interface OnInitializedListener {
        fun onInitialize()
    }
}