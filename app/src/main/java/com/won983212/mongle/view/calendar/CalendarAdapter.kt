package com.won983212.mongle.view.calendar

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.won983212.mongle.util.createDateTimeFromEpoch
import com.won983212.mongle.util.epochSeconds
import com.won983212.mongle.util.withFirstDayOfMonth
import org.threeten.bp.LocalDate

class CalendarAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    private var monthFirstDay = LocalDate.now().withFirstDayOfMonth().epochSeconds

    override fun getItemCount(): Int = Int.MAX_VALUE

    override fun createFragment(position: Int): Fragment {
        Log.d("AA", position.toString())
        return CalendarMonthFragment.of(getItemId(position))
    }

    override fun getItemId(position: Int): Long =
        createDateTimeFromEpoch(monthFirstDay).plusMonths((position - START_POSITION).toLong()).epochSeconds

    override fun containsItem(itemId: Long): Boolean {
        val date = createDateTimeFromEpoch(itemId)

        return date.dayOfMonth == 1 && date.epochSeconds == 0L
    }

    companion object {
        const val START_POSITION = Int.MAX_VALUE / 2
    }
}