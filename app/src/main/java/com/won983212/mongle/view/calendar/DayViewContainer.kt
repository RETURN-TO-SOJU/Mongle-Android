package com.won983212.mongle.view.calendar

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.ViewContainer
import com.won983212.mongle.base.Emotion
import com.won983212.mongle.R
import com.won983212.mongle.util.setTextColorRes
import java.time.LocalDate

enum class DayType {
    DIGIT, EMOTION
}

class DayViewContainer(view: View, dayClickListener: (LocalDate) -> Unit) :
    ViewContainer(view) {

    private lateinit var day: CalendarDay
    private var dayType: DayType = DayType.DIGIT

    val todayIndicator = view.findViewById<ImageView>(R.id.dotIsToday)

    // digit day view
    val dayTextView = view.findViewById<TextView>(R.id.calendarDayText)
    val selectionBackground = view.findViewById<ImageView>(R.id.daySelectionBackground)

    // emotion view
    val selectionShadow = view.findViewById<ImageView>(R.id.selectionShadow)
    val emotionImage = view.findViewById<ImageView>(R.id.emotionImage)

    init {
        view.setOnClickListener {
            if (day.owner == DayOwner.THIS_MONTH) {
                dayClickListener(day.date)
            }
        }
    }

    fun initialize(day: CalendarDay) {
        this.day = day
        dayTextView.text = day.date.dayOfMonth.toString()
        dayTextView.setTextColorRes(R.color.text)
        selectionBackground.visibility = View.GONE
        selectionShadow.visibility = View.GONE
    }

    fun setToday() {
        todayIndicator.visibility = View.VISIBLE
    }

    fun setDisabled() {
        dayTextView.setTextColorRes(R.color.disabled)
    }

    fun setSelected() {
        if (dayType == DayType.DIGIT) {
            dayTextView.setTextColorRes(R.color.on_point)
            selectionBackground.visibility = View.VISIBLE
        } else {
            selectionShadow.visibility = View.VISIBLE
        }
    }

    fun setEmotion(emotion: Emotion) {
        dayType = DayType.EMOTION
        dayTextView.visibility = View.GONE
        selectionBackground.visibility = View.GONE
        selectionShadow.visibility = View.GONE
        emotionImage.visibility = View.VISIBLE
        emotionImage.setImageResource(emotion.iconRes)
    }
}