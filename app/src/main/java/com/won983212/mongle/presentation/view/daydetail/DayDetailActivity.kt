package com.won983212.mongle.presentation.view.daydetail

import android.content.Intent
import androidx.activity.viewModels
import com.won983212.mongle.R
import com.won983212.mongle.data.model.Emotion
import com.won983212.mongle.databinding.ActivityDayDetailBinding
import com.won983212.mongle.presentation.base.BaseDataActivity
import com.won983212.mongle.presentation.view.daydetail.DayDetailActivity.Companion.EXTRA_DATE
import com.won983212.mongle.presentation.view.daydetail.DayDetailActivity.Companion.EXTRA_SHOW_ARRIVED_GIFT_DIALOG
import com.won983212.mongle.presentation.view.daydetail.adapter.AnalyzedEmotionListAdapter
import com.won983212.mongle.presentation.view.daydetail.adapter.PhotoListAdapter
import com.won983212.mongle.presentation.view.daydetail.adapter.ScheduleListAdapter
import com.won983212.mongle.presentation.view.diary.EditDiaryActivity
import com.won983212.mongle.presentation.view.messages.EmotionMessagesActivity
import com.won983212.mongle.presentation.view.openGiftArrivedDialog
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * ## Extras
 * * **(필수)** [EXTRA_DATE]: [LocalDate] -
 * 어떤 날을 상세보기로 볼 것인지 정한다.
 * * **(선택)** [EXTRA_SHOW_ARRIVED_GIFT_DIALOG]: [Boolean] -
 * 선물 도착 Dialog를 띄운다. 기본값은 false
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

        // TODO DateTimeFormatter들 한곳에 모아서 정의해도 좋을듯
        viewModel.eventOpenGiftDialog.observe(this) {
            openGiftArrivedDialog(this, it.format(DateTimeFormatter.ofPattern("yyyy.MM.dd")))
        }
        viewModel.attachDefaultLoadingHandler(this)
        viewModel.attachDefaultErrorHandler(this)
        viewModel.initializeFromIntent(intent)
        initRecyclerList()
    }

    private fun initRecyclerList() {
        binding.listDayDetailAnalyzedEmotion.adapter = AnalyzedEmotionListAdapter {
            Intent(this, EmotionMessagesActivity::class.java).apply {
                putExtra(EmotionMessagesActivity.EXTRA_DATE, viewModel.date)
                putExtra(EmotionMessagesActivity.EXTRA_EMOTION, it)

                val proportionMap = viewModel.analyzedEmotions.value?.associate {
                    it.emotion to it.proportion
                }
                if (proportionMap != null) {
                    val enumMap = EnumMap(Emotion.values().associateWith { proportionMap[it] ?: 0 })
                    putExtra(EmotionMessagesActivity.EXTRA_PROPORTIONS, enumMap)
                }
                startActivity(this)
            }
        }
        binding.listDayDetailPhoto.adapter = PhotoListAdapter()
        binding.listDayDetailSchedule.adapter = ScheduleListAdapter()
    }

    companion object {
        const val EXTRA_DATE = "date"
        const val EXTRA_SHOW_ARRIVED_GIFT_DIALOG = "gift"
    }
}