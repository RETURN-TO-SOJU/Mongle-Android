package com.won983212.mongle.presentation.view.daydetail.weekselector

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.won983212.mongle.R
import com.won983212.mongle.common.util.setTextColorRes
import com.won983212.mongle.databinding.WeekCalendarDayBinding

class WeekDay @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val binding = WeekCalendarDayBinding.inflate(LayoutInflater.from(context), this, false)

    var weekDayText: String = ""
        set(value) {
            field = value
            updateUIState()
        }

    var dayText: String = ""
        set(value) {
            field = value
            updateUIState()
        }


    init {
        isClickable = true
        isFocusable = true

        if (attrs != null) {
            setupAttributes(attrs, defStyleAttr, defStyleRes)
        } else {
            isSelected = false
        }

        addView(binding.root)
    }

    private fun setupAttributes(attrsIn: AttributeSet, defStyleAttr: Int, defStyleRes: Int) {
        val attrs =
            context.obtainStyledAttributes(attrsIn, R.styleable.WeekDay, defStyleAttr, defStyleRes)
        isSelected = attrs.getBoolean(R.styleable.WeekDay_isSelected, false)
        attrs.recycle()
    }

    override fun setSelected(selected: Boolean) {
        super.setSelected(selected)
        updateUIState()
    }

    private fun updateUIState() {
        if (isSelected) {
            binding.root.setBackgroundResource(R.drawable.ripple_round_25)
            binding.textWeekCalendarWeekday.setTextColorRes(R.color.white)
            binding.textWeekCalendarDay.setBackgroundResource(R.drawable.ripple_round_25)
            binding.textWeekCalendarDay.setTextColorRes(R.color.point)
        } else {
            binding.root.setBackgroundResource(0)
            binding.textWeekCalendarWeekday.setTextColorRes(R.color.disabled)
            binding.textWeekCalendarDay.setBackgroundResource(0)
            binding.textWeekCalendarDay.setTextColorRes(R.color.disabled)
        }
        binding.textWeekCalendarWeekday.text = weekDayText
        binding.textWeekCalendarDay.text = dayText
    }
}