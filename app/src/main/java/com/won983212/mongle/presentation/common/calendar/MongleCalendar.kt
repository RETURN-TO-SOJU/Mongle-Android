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
import com.won983212.mongle.databinding.CalendarMongleBinding
import com.won983212.mongle.domain.model.Emotion
import com.won983212.mongle.presentation.base.event.OnSelectedListener
import com.won983212.mongle.presentation.util.dpToPxInt
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
    private var monthLoadedListener: OnMonthLoadedListener? = null

    private var startMonth: YearMonth
    private var endMonth: YearMonth

    init {
        val currentMonth = YearMonth.now()
        val firstDayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek
        val daysOfWeek = resources.getStringArray(R.array.calendar_weekdays)

        startMonth = currentMonth.minusMonths(MONTH_LOAD_BATCH_SIZE)
        endMonth = currentMonth.plusMonths(MONTH_LOAD_BATCH_SIZE)

        binding.calendar.apply {
            daySize = CalendarView.sizeAutoWidth(dpToPxInt(context, 44))
            dayBinder = MongleDayBinder(this@MongleCalendar, this@MongleCalendar::selectDate)
            monthHeaderBinder = MongleMonthWeekHeaderBinder(daysOfWeek)
            monthScrollListener = {
                binding.textCalendarMonth.text =
                    DatetimeFormats.MONTH_DOT_SPACE.format(it.yearMonth)
                if (it.yearMonth == startMonth) {
                    val prevStartMonth = startMonth
                    startMonth = startMonth.minusMonths(MONTH_LOAD_BATCH_SIZE)
                    updateMonthRangeAsync(startMonth, endMonth)
                    monthLoadedListener?.onLoaded(startMonth, prevStartMonth)
                }
                if (it.yearMonth == endMonth) {
                    val prevEndMonth = endMonth
                    endMonth = endMonth.plusMonths(MONTH_LOAD_BATCH_SIZE)
                    updateMonthRangeAsync(startMonth, endMonth)
                    monthLoadedListener?.onLoaded(prevEndMonth, endMonth)
                }
            }
            isNestedScrollingEnabled = false
            setupAsync(startMonth, endMonth, firstDayOfWeek) {
                this.scrollToMonth(currentMonth)
                dayEmotions?.let { setDayEmotions(it) }
                initializedListener?.onInitialize()
                monthLoadedListener?.onLoaded(startMonth, endMonth)
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

    /**
     * 지금까지 불러온 감정들에 추가로 더 데이터를 추가합니다.
     * 입력된 모든 day 데이터들 [emotionMapping]에 대해 무조건 ui update합니다.
     * 이미 있는 데이터들은 교체되고, 마찬가지로 ui update를 실시합니다.
     */
    fun addDayEmotions(emotionMapping: Map<LocalDate, Emotion>) {
        dayEmotions = dayEmotions?.plus(emotionMapping) ?: emotionMapping
        if (binding.calendar.adapter != null) {
            for (day in emotionMapping.keys) {
                binding.calendar.notifyDateChanged(day)
            }
        }
    }

    /**
     * 기존에 있던 데이터를 삭제하고 새로운 데이터로 대체합니다.
     * 기존 데이터, 새로운 데이터 사이에 변화된 부분만 ui update합니다.
     */
    private fun setDayEmotions(emotionMapping: Map<LocalDate, Emotion>) {
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

    fun setOnSelectedListener(listener: OnSelectedListener<LocalDate>) {
        selectedListener = listener
    }

    fun setOnInitializedListener(listener: OnInitializedListener) {
        initializedListener = listener
    }

    fun setOnMonthLoadedListener(listener: OnMonthLoadedListener) {
        monthLoadedListener = listener
    }

    companion object {
        private const val MONTH_LOAD_BATCH_SIZE = 5L
    }


    fun interface OnInitializedListener {
        fun onInitialize()
    }

    fun interface OnMonthLoadedListener {
        fun onLoaded(from: YearMonth, to: YearMonth)
    }
}