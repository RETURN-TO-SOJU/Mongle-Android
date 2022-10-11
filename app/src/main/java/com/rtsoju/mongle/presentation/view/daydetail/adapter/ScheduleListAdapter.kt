package com.rtsoju.mongle.presentation.view.daydetail.adapter

import com.rtsoju.mongle.R
import com.rtsoju.mongle.databinding.ListitemScheduleBinding
import com.rtsoju.mongle.presentation.base.BaseRecyclerAdapter
import com.rtsoju.mongle.presentation.view.daydetail.model.SchedulePresentationModel

class ScheduleListAdapter :
    BaseRecyclerAdapter<ListitemScheduleBinding, SchedulePresentationModel>() {

    override val itemLayoutId: Int = R.layout.listitem_schedule

    override fun bind(binding: ListitemScheduleBinding, item: SchedulePresentationModel) {
        binding.textListitemScheduleTitle.text = item.name
        binding.textListitemScheduleSubtitle.text = item.sourceText
        binding.textListitemScheduleTime.text = item.timeRangeText
    }
}