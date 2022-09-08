package com.won983212.mongle.presentation.view.daydetail.adapter

import com.won983212.mongle.R
import com.won983212.mongle.databinding.ListitemScheduleBinding
import com.won983212.mongle.presentation.base.BaseRecyclerAdapter
import com.won983212.mongle.presentation.view.daydetail.model.Schedule

class ScheduleListAdapter :
    BaseRecyclerAdapter<ListitemScheduleBinding, Schedule>() {

    override val itemLayoutId: Int = R.layout.listitem_schedule

    override fun bind(binding: ListitemScheduleBinding, item: Schedule) {
        binding.textListitemScheduleTitle.text = item.name
        binding.textListitemScheduleSubtitle.text = item.sourceText
        binding.textListitemScheduleTime.text = item.timeRangeText
    }
}