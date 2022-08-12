package com.won983212.mongle.view.calendar

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.ViewContainer
import com.won983212.mongle.R
import com.won983212.mongle.base.Emotion
import com.won983212.mongle.util.setTextColorRes
import java.time.LocalDate

enum class DayType {
    DIGIT, EMOTION
}

class DayViewContainer(view: View, dayClickListener: (LocalDate) -> Unit) :
    ViewContainer(view) {

    private lateinit var day: CalendarDay
    private var dayType: DayType = DayType.DIGIT

    val todayIndicator = view.findViewById<ImageView>(R.id.image_today_dot)

    // digit day view
    val dayTextView = view.findViewById<TextView>(R.id.text_day)
    val daySelection = view.findViewById<ImageView>(R.id.image_day_selection)

    // emotion view
    val emotionSelection = view.findViewById<ImageView>(R.id.image_emotion_selected)
    val emotionImage = view.findViewById<ImageView>(R.id.image_emotion)

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
        daySelection.visibility = View.GONE
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