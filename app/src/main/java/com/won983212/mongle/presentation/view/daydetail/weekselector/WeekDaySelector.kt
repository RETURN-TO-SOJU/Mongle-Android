package com.won983212.mongle.presentation.view.daydetail.weekselector

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.children
import com.won983212.mongle.R
import com.won983212.mongle.presentation.base.event.OnSelectedListener
import java.time.LocalDate

class WeekDaySelector @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyleAttr, defStyleRes), View.OnClickListener {

    private var selectedIndex: Int = -1

    val selected: LocalDate?
        get() {
            if (selectedIndex < 0) {
                return null
            }
            return startDay.plusDays(selectedIndex.toLong())
        }

    var startDay: LocalDate = LocalDate.now()
        set(value) {
            field = value
            updateUI()
        }

    private var selectionChangedListener: OnSelectedListener<LocalDate>? = null


    init {
        for (i in 0 until COUNT_OF_DAYS) {
            val element = WeekDay(context).apply {
                tag = i
                setOnClickListener(this@WeekDaySelector)
            }
            val params = LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f)
            addView(element, params)
        }
        updateUI()
    }

    fun selectIndex(index: Int) {
        if (selectedIndex != index && index in 0 until COUNT_OF_DAYS) {
            (getChildAt(index) as WeekDay).isSelected = true
            if (selectedIndex in 0 until COUNT_OF_DAYS) {
                (getChildAt(selectedIndex) as WeekDay).isSelected = false
            }

            selectedIndex = index
            selectionChangedListener?.onSelected(startDay.plusDays(index.toLong()))
        }
    }

    override fun onClick(v: View?) {
        if (v != null && v is WeekDay) {
            selectIndex(v.tag as Int)
        }
    }

    fun setOnSelectionChangedListener(listener: OnSelectedListener<LocalDate>) {
        selectionChangedListener = listener
    }

    private fun updateUI() {
        val weekDayTexts = resources.getStringArray(R.array.calendar_weekdays)
        children.forEach { view ->
            if (view is WeekDay) {
                val date = startDay.plusDays((view.tag as Int).toLong())
                view.weekDayText = weekDayTexts[date.dayOfWeek.value % 7]
                view.dayText = date.dayOfMonth.toString()
            }
        }
    }

    companion object {
        private const val COUNT_OF_DAYS = 7
    }
}