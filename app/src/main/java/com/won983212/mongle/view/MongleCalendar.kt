package com.won983212.mongle.view

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.graphics.drawable.InsetDrawable
import android.text.style.TextAppearanceSpan
import android.util.AttributeSet
import android.util.TypedValue
import androidx.core.content.ContextCompat
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.format.ArrayWeekDayFormatter
import com.won983212.mongle.R
import org.threeten.bp.format.DateTimeFormatter

/**
 * Mongle의 스타일을 적용하면서, customizing 가능한 캘린더.
 * 직접 xml에서 스타일을 재정의해서 customizing 할 수 있다.
 */
// TODO Calendar loading is too slow
class MongleCalendar : MaterialCalendarView {
    constructor(context: Context) : super(context)

    // 스타일을 재정의할 수 있게 하기 위해서
    // 아래와 같이 attrArr을 활용해서 생성자를 구현한다.
    constructor(context: Context, attr: AttributeSet) : super(context, attr) {
        val attrArr = context.theme
            .obtainStyledAttributes(
                attr,
                com.prolificinteractive.materialcalendarview.R.styleable.MaterialCalendarView,
                0,
                0
            )

        try {
            setupCalendarConfig(attrArr)
            setupHeaderStyle(attrArr)
            setupCalendarStyle(attrArr)
        } finally {
            attrArr.recycle()
        }

        setupDateFormatter()
        attachDecorators()
    }

    private fun setupCalendarConfig(attrArr: TypedArray) {
        showOtherDates = attrArr.getInteger(
            com.prolificinteractive.materialcalendarview.R.styleable.MaterialCalendarView_mcv_showOtherDates,
            SHOW_OTHER_MONTHS
        )
        isDynamicHeightEnabled = false

        val defTileWidth = context.resources.getDimension(R.dimen.calendar_tile_width).toInt()
        tileWidth = attrArr.getLayoutDimension(
            com.prolificinteractive.materialcalendarview.R.styleable.MaterialCalendarView_mcv_tileWidth,
            defTileWidth
        )

        val defTileHeight = context.resources.getDimension(R.dimen.calendar_tile_height).toInt()
        tileHeight = attrArr.getLayoutDimension(
            com.prolificinteractive.materialcalendarview.R.styleable.MaterialCalendarView_mcv_tileHeight,
            defTileHeight
        )
    }

    private fun setupDateFormatter() {
        setWeekDayFormatter(
            ArrayWeekDayFormatter(
                getResources().getTextArray(R.array.calendar_weekdays)
            )
        )

        setTitleFormatter { day ->
            day?.date?.format(DateTimeFormatter.ofPattern("YYYY.MM"))
        }
    }

    private fun setupCalendarStyle(attrArr: TypedArray) {
        selectionColor = attrArr.getColor(
            com.prolificinteractive.materialcalendarview.R.styleable.MaterialCalendarView_mcv_selectionColor,
            resources.getColor(R.color.point)
        )

        setWeekDayTextAppearance(
            attrArr.getResourceId(
                com.prolificinteractive.materialcalendarview.R.styleable.MaterialCalendarView_mcv_weekDayTextAppearance,
                R.style.CalendarWeekDayText
            )
        )

        setDateTextAppearance(
            attrArr.getResourceId(
                com.prolificinteractive.materialcalendarview.R.styleable.MaterialCalendarView_mcv_dateTextAppearance,
                R.style.CalendarDayText
            )
        )
    }

    private fun setupHeaderStyle(attrArr: TypedArray) {
        setLeftArrow(
            attrArr.getResourceId(
                com.prolificinteractive.materialcalendarview.R.styleable.MaterialCalendarView_mcv_leftArrow,
                R.drawable.ic_arrow_left
            )
        )

        setRightArrow(
            attrArr.getResourceId(
                com.prolificinteractive.materialcalendarview.R.styleable.MaterialCalendarView_mcv_rightArrow,
                R.drawable.ic_arrow_right
            )
        )

        setHeaderTextAppearance(
            attrArr.getResourceId(
                com.prolificinteractive.materialcalendarview.R.styleable.MaterialCalendarView_mcv_headerTextAppearance,
                R.style.CalendarHeaderText
            )
        )
    }

    private fun attachDecorators() {
        val selectionDrawable = InsetDrawable(
            ContextCompat.getDrawable(
                context,
                R.drawable.calendar_day_selector
            ),
            12,
            8,
            12,
            8
        )

        addDecorators(DayDecorator(selectionDrawable))
        addDecorators(TodayDecorator(context))
    }

    private class TodayDecorator(val context: Context) : DayViewDecorator {
        private val today = CalendarDay.today()

        override fun shouldDecorate(day: CalendarDay) = day == today

        override fun decorate(view: DayViewFacade) {
            view.addSpan(TextAppearanceSpan(context, R.style.CalendarTodayText))
        }
    }

    private class DayDecorator(val selectionDrawable: Drawable) : DayViewDecorator {
        override fun shouldDecorate(day: CalendarDay) = true

        override fun decorate(view: DayViewFacade) {
            view.setSelectionDrawable(selectionDrawable)
        }
    }
}