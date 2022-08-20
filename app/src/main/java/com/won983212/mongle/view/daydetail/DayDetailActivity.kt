package com.won983212.mongle.view.daydetail

import androidx.activity.viewModels
import com.won983212.mongle.R
import com.won983212.mongle.common.base.BaseDataActivity
import com.won983212.mongle.databinding.ActivityDayDetailBinding
import com.won983212.mongle.view.daydetail.adapter.AnalyzedEmotionListAdapter
import com.won983212.mongle.view.daydetail.adapter.PhotoListAdapter
import com.won983212.mongle.view.daydetail.adapter.ScheduleListAdapter

class DayDetailActivity : BaseDataActivity<ActivityDayDetailBinding>() {

    private val viewModel by viewModels<DayDetailViewModel>()

    override val layoutId: Int = R.layout.activity_day_detail

    override fun onInitialize() {
        binding.viewModel = viewModel
        setSupportActionBar(binding.toolbarDayDetail)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.btnDayDetailBack.setOnClickListener {
            finish()
        }

        initRecyclerList()
    }

    private fun initRecyclerList() {
        binding.listDayDetailAnalyzedEmotion.adapter = AnalyzedEmotionListAdapter()
        binding.listDayDetailPhoto.adapter = PhotoListAdapter()
        binding.listDayDetailSchedule.adapter = ScheduleListAdapter()
    }
}