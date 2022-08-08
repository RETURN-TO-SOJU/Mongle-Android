package com.won983212.mongle.view.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.won983212.mongle.databinding.FragmentMongleCalendarBinding

class MongleCalendar : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = FragmentMongleCalendarBinding.inflate(inflater)
        view.calendarView.apply {
            adapter = CalendarAdapter(requireActivity())
            setCurrentItem(Int.MAX_VALUE / 2, false)
        }
        return view.root
    }
}