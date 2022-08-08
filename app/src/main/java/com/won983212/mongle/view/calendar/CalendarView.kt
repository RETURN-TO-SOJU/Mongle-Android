package com.won983212.mongle.view.calendar

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes
import androidx.core.content.withStyledAttributes
import androidx.core.view.children
import com.won983212.mongle.R
import org.threeten.bp.LocalDate
import java.lang.Integer.max

class CalendarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = R.attr.calendarViewStyle,
    @StyleRes defStyleRes: Int = R.style.Calendar_CalendarViewStyle
) : ViewGroup(context, attrs, defStyleAttr, defStyleRes) {

    private var _height: Float = 0f

    init {
        context.withStyledAttributes(attrs, R.styleable.MongleCalendar, defStyleAttr, defStyleRes) {
            _height = getDimension(R.styleable.MongleCalendar_dayHeight, 0f)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val h = paddingTop + paddingBottom + max(
            suggestedMinimumHeight,
            (_height * 6).toInt()
        )
        setMeasuredDimension(getDefaultSize(suggestedMinimumWidth, widthMeasureSpec), h)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val iWidth = (width / 7).toFloat()
        val iHeight = (height / 6).toFloat()

        var index = 0
        children.forEach { view ->
            val left = (index % 7) * iWidth
            val top = (index / 7) * iHeight

            view.layout(left.toInt(), top.toInt(), (left + iWidth).toInt(), (top + iHeight).toInt())

            index++
        }
    }

    fun initCalendar(firstDayOfMonth: LocalDate, list: List<LocalDate>) {
        list.forEach {
            addView(
                DayItemView(
                    context = context,
                    date = it,
                    firstDayOfMonth = firstDayOfMonth
                )
            )
        }
    }
}