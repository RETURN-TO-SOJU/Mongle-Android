package com.won983212.mongle.view.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.won983212.mongle.databinding.FragmentMonthDaysBinding
import com.won983212.mongle.util.createDateTimeFromEpoch
import com.won983212.mongle.util.getMonthList

class CalendarMonthFragment : Fragment() {
    private var epochSeconds: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            epochSeconds = it.getLong(EPOCH_SECONDS)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = FragmentMonthDaysBinding.inflate(inflater, container, false)
        val dateTime = createDateTimeFromEpoch(epochSeconds)
        view.calendarView.initCalendar(dateTime, dateTime.getMonthList())
        return view.root
    }

    companion object {

        private const val EPOCH_SECONDS = "EPOCH_SECONDS"

        fun of(epochSeconds: Long) = CalendarMonthFragment().apply {
            arguments = Bundle().apply {
                putLong(EPOCH_SECONDS, epochSeconds)
            }
        }
    }
}