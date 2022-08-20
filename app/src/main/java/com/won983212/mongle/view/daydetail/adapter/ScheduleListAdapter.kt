package com.won983212.mongle.view.daydetail.adapter

import android.view.ViewGroup
import com.won983212.mongle.R
import com.won983212.mongle.common.base.BaseRecyclerAdapter
import com.won983212.mongle.databinding.ListitemScheduleBinding
import com.won983212.mongle.view.daydetail.model.Schedule

class ScheduleListAdapter :
    BaseRecyclerAdapter<ListitemScheduleBinding, Schedule>() {

    override val itemLayoutId: Int = R.layout.listitem_schedule

    override fun bind(binding: ListitemScheduleBinding, item: Schedule) {
        binding.textListitemScheduleTitle.text = item.name
        binding.textListitemScheduleSubtitle.text = item.sourceText
        binding.textListitemScheduleTime.text = item.timeRangeText
    }
}