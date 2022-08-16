package com.won983212.mongle.view.daydetail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.won983212.mongle.R
import com.won983212.mongle.view.daydetail.model.Schedule

class ScheduleListAdapter(
    private val schedules: List<Schedule>
) : RecyclerView.Adapter<ScheduleListAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textTitle: TextView
        val textSource: TextView
        val textTimeRange: TextView

        init {
            textTitle = view.findViewById(R.id.text_title)
            textSource = view.findViewById(R.id.text_subtitle)
            textTimeRange = view.findViewById(R.id.text_time_schedule)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_item_schedule, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val schedule = schedules[position]
        holder.textTitle.text = schedule.name
        holder.textSource.text = schedule.sourceText
        holder.textTimeRange.text = schedule.timeRangeText
    }

    override fun getItemCount(): Int = schedules.size
}