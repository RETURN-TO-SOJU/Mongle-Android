package com.won983212.mongle.presentation.view.daydetail

import android.content.Intent
import androidx.activity.viewModels
import com.won983212.mongle.R
import com.won983212.mongle.databinding.ActivityDayDetailBinding
import com.won983212.mongle.presentation.base.BaseDataActivity
import com.won983212.mongle.presentation.view.daydetail.adapter.AnalyzedEmotionListAdapter
import com.won983212.mongle.presentation.view.daydetail.adapter.PhotoListAdapter
import com.won983212.mongle.presentation.view.daydetail.adapter.ScheduleListAdapter
import com.won983212.mongle.presentation.view.diary.EditDiaryActivity
import java.time.LocalDate

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

        binding.textDayDetailDiary.setOnClickListener {
            Intent(this, EditDiaryActivity::class.java).apply {
                // TODO mocking data
                val date = LocalDate.now()
                putExtra(EditDiaryActivity.EXTRA_DATE, date)
                putExtra(EditDiaryActivity.EXTRA_EMOTION, 0)
                putExtra(EditDiaryActivity.EXTRA_INITIAL_DIARY, "")
                startActivity(this)
            }
        }

        initRecyclerList()
    }

    private fun initRecyclerList() {
        binding.listDayDetailAnalyzedEmotion.adapter = AnalyzedEmotionListAdapter()
        binding.listDayDetailPhoto.adapter = PhotoListAdapter()
        binding.listDayDetailSchedule.adapter = ScheduleListAdapter()
    }
}