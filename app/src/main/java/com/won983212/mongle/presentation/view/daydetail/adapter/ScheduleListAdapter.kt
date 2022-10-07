package com.won983212.mongle.presentation.view.daydetail.adapter

import com.won983212.mongle.R
import com.won983212.mongle.databinding.ListitemScheduleBinding
import com.won983212.mongle.presentation.base.BaseRecyclerAdapter
import com.won983212.mongle.presentation.view.daydetail.model.SchedulePresentationModel

class ScheduleListAdapter :
    BaseRecyclerAdapter<ListitemScheduleBinding, SchedulePresentationModel>() {

    override val itemLayoutId: Int = R.layout.listitem_schedule

    override fun bind(binding: ListitemScheduleBinding, item: SchedulePresentationModel) {
        binding.textListitemScheduleTitle.text = item.name
        binding.textListitemScheduleSubtitle.text = item.sourceText
        binding.textListitemScheduleTime.text = item.timeRangeText
    }
}