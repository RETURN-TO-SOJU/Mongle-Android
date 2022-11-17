package com.rtsoju.mongle.presentation.common.calendar

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.kizitonwose.calendarview.CalendarView
import com.kizitonwose.calendarview.utils.next
import com.kizitonwose.calendarview.utils.previous
import com.kizitonwose.calendarview.utils.yearMonth
import com.rtsoju.mongle.R
import com.rtsoju.mongle.databinding.ControlCalendarMongleBinding
import com.rtsoju.mongle.domain.model.Emotion
import com.rtsoju.mongle.presentation.base.event.OnSelectedListener
import com.rtsoju.mongle.presentation.util.dpToPxInt
import com.rtsoju.mongle.util.DatetimeFormats
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

    private val binding = ControlCalendarMongleBinding.inflate(LayoutInflater.from(context))

    var selectedDate: LocalDate? = null
        private set

    var dayEmotions: Map<LocalDate, Emotion>? = null
        private set

    private var selectedListener: OnSelectedListener<LocalDate>? = null
    private var clickSelectedListener: OnSelectedListener<LocalDate>? = null
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
            dayBinder = MongleDayBinder(this@MongleCalendar, this@MongleCalendar::clickDay)
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

    private fun clickDay(date: LocalDate?) {
        if (!selectDate(date) && date != null) {
            clickSelectedListener?.onSelected(date)
        }
    }

    /**
     * [date]를 선택한다. 선택된 Day는 highlight되고, 현재 month에 없으면 해당 month로 스크롤된다.
     * SelectedListener가 등록되어있다면 호출된다. 만약 [date]를 null로 지정하면 선택을 취소한다.
     * 이때는 SelectedListener가 호출되지않는다.
     * @param date 선택할 date
     * @return 선택(또는 선택 취소)에 성공할 경우 true리턴한다.
     * 성공하는 경우는 기존에 선택된 날짜와 [date]가 다른 경우이다.
     */
    fun selectDate(date: LocalDate?): Boolean {
        val hasDirty = selectedDate != date
        if (hasDirty) {
            val oldDate = selectedDate
            selectedDate = date

            oldDate?.let { binding.calendar.notifyDateChanged(it) }
            date?.let {
                binding.calendar.scrollToMonth(date.yearMonth)
                binding.calendar.notifyDateChanged(date)
                selectedListener?.onSelected(date)
            }
        }
        return hasDirty
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

    /**
     * Calendar의 특정 Day가 선택되었을 때 발생한다.
     */
    fun setOnSelectedListener(listener: OnSelectedListener<LocalDate>) {
        selectedListener = listener
    }

    /**
     * 이미 선택된 Day가 클릭되었을 때 발생한다.
     */
    fun setOnClickSelectedListener(listener: OnSelectedListener<LocalDate>) {
        clickSelectedListener = listener
    }

    /**
     * Calendar가 사용가능한 상태(Calendar의 메서드들을 사용할 수 있는 상태)가 되었을 때 발생한다.
     */
    fun setOnInitializedListener(listener: OnInitializedListener) {
        initializedListener = listener
    }

    /**
     * 캐시된 month를 지나, 새로운 month가 load되었을 때 발생한다.
     */
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