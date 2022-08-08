package com.won983212.mongle.view.calendar

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.content.withStyledAttributes
import com.won983212.mongle.R
import com.won983212.mongle.util.isSameMonth
import com.won983212.mongle.util.withFirstDayOfMonth
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime

class DayItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes private val defStyleAttr: Int = R.attr.itemViewStyle,
    @StyleRes private val defStyleRes: Int = R.style.Calendar_ItemViewStyle,
    private val date: LocalDate = LocalDate.now(),
    private val firstDayOfMonth: LocalDate = LocalDate.now().withFirstDayOfMonth()
) : View(context, attrs, defStyleAttr, defStyleRes) {

    private val bounds = Rect()

    private var paint: Paint = Paint()

    init {
        /* Attributes */
        context.withStyledAttributes(attrs, R.styleable.MongleCalendar, defStyleAttr, defStyleRes) {
            val dayTextSize =
                getDimensionPixelSize(R.styleable.MongleCalendar_dayTextSize, 0).toFloat()

            /* 흰색 배경에 유색 글씨 */
            paint = TextPaint().apply {
                isAntiAlias = true
                textSize = dayTextSize
                color = Color.BLUE
                if (!date.isSameMonth(firstDayOfMonth)) {
                    alpha = 50
                }
            }
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas == null) return

        val date = date.dayOfMonth.toString()
        paint.getTextBounds(date, 0, date.length, bounds)
        canvas.drawText(
            date,
            (width / 2 - bounds.width() / 2).toFloat() - 2,
            (height / 2 + bounds.height() / 2).toFloat(),
            paint
        )
    }
}