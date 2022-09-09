package com.won983212.mongle.presentation.component.calendar

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.ViewContainer
import com.won983212.mongle.R
import com.won983212.mongle.common.util.setTextColorRes
import com.won983212.mongle.data.model.Emotion
import java.time.LocalDate

enum class DayType {
    DIGIT, EMOTION
}

internal class DayViewContainer(view: View, dayClickListener: (LocalDate) -> Unit) :
    ViewContainer(view) {

    private lateinit var day: CalendarDay
    private var dayType: DayType = DayType.DIGIT

    private val todayIndicator = view.findViewById<ImageView>(R.id.image_calendar_day_today_dot)

    // digit day view
    private val dayTextView = view.findViewById<TextView>(R.id.text_calendar_day)
    private val daySelection = view.findViewById<ImageView>(R.id.image_calendar_day_selection)

    // emotion view
    private val emotionSelection =
        view.findViewById<ImageView>(R.id.image_calendar_day_emotion_selected)
    private val emotionImage = view.findViewById<ImageView>(R.id.image_calendar_day_emotion)

    init {
        view.setOnClickListener {
            if (day.owner == DayOwner.THIS_MONTH) {
                dayClickListener(day.date)
            }
        }
    }

    fun initialize(day: CalendarDay) {
        this.day = day
        dayTextView.visibility = View.VISIBLE
        dayTextView.text = day.date.dayOfMonth.toString()
        dayTextView.setTextColorRes(R.color.text)
        daySelection.visibility = View.GONE
        emotionImage.visibility = View.GONE
        emotionSelection.visibility = View.GONE
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
            daySelection.visibility = View.VISIBLE
        } else {
            emotionSelection.visibility = View.VISIBLE
        }
    }

    fun setEmotion(emotion: Emotion) {
        dayType = DayType.EMOTION
        dayTextView.visibility = View.GONE
        daySelection.visibility = View.GONE
        emotionSelection.visibility = View.GONE
        emotionImage.visibility = View.VISIBLE
        emotionImage.setImageResource(emotion.iconRes)
    }
}