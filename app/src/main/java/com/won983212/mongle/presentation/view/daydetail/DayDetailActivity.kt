package com.won983212.mongle.presentation.view.daydetail

import android.content.Intent
import androidx.activity.viewModels
import com.won983212.mongle.R
import com.won983212.mongle.databinding.ActivityDayDetailBinding
import com.won983212.mongle.presentation.base.BaseDataActivity
import com.won983212.mongle.presentation.view.daydetail.DayDetailActivity.Companion.EXTRA_DATE
import com.won983212.mongle.presentation.view.daydetail.adapter.AnalyzedEmotionListAdapter
import com.won983212.mongle.presentation.view.daydetail.adapter.PhotoListAdapter
import com.won983212.mongle.presentation.view.daydetail.adapter.ScheduleListAdapter
import com.won983212.mongle.presentation.view.diary.EditDiaryActivity
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate

/**
 * ## Extras
 * * **(필수)** [EXTRA_DATE]: [LocalDate] -
 * 어떤 날을 상세보기로 볼 것인지 정한다.
 */
@AndroidEntryPoint
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
                val emotion = viewModel.emotion
                if (emotion != null) {
                    putExtra(EditDiaryActivity.EXTRA_EMOTION_RES, emotion.iconRes)
                }
                putExtra(EditDiaryActivity.EXTRA_DATE, viewModel.date)
                putExtra(EditDiaryActivity.EXTRA_INITIAL_DIARY, viewModel.diary.value)
                startActivity(this)
            }
        }

        viewModel.attachDefaultLoadingHandler(this)
        viewModel.attachDefaultErrorHandler(this)
        viewModel.initializeFromIntent(intent)
        initRecyclerList()
    }

    private fun initRecyclerList() {
        binding.listDayDetailAnalyzedEmotion.adapter = AnalyzedEmotionListAdapter()
        binding.listDayDetailPhoto.adapter = PhotoListAdapter()
        binding.listDayDetailSchedule.adapter = ScheduleListAdapter()
    }

    companion object {
        const val EXTRA_DATE = "date"
    }
}