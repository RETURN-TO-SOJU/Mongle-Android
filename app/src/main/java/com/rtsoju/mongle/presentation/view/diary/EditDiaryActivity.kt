package com.rtsoju.mongle.presentation.view.diary

import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import com.rtsoju.mongle.R
import com.rtsoju.mongle.databinding.ActivityEditDiaryBinding
import com.rtsoju.mongle.presentation.base.BaseDataActivity
import com.rtsoju.mongle.presentation.view.diary.EditDiaryActivity.Companion.EXTRA_DATE
import com.rtsoju.mongle.presentation.view.diary.EditDiaryActivity.Companion.EXTRA_EMOTION_RES
import com.rtsoju.mongle.presentation.view.diary.EditDiaryActivity.Companion.EXTRA_INITIAL_DIARY
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate

/**
 * ## Extras
 * * **(선택)** [EXTRA_DATE]: [LocalDate] -
 * 일기장 상단에 표시될 제목. 기본값은 오늘 날짜
 * * **(선택)** [EXTRA_INITIAL_DIARY]: [String] -
 * 초기 일기장 텍스트. 기본값은 empty string
 * * **(선택)** [EXTRA_EMOTION_RES]: [DrawableRes] -
 * 일기장에 표시될 감정 Icon Resource. 기본값은 @drawable/ic_edit
 */
@AndroidEntryPoint
class EditDiaryActivity : BaseDataActivity<ActivityEditDiaryBinding>() {

    override val layoutId: Int = R.layout.activity_edit_diary
    private val viewModel by viewModels<EditDiaryViewModel>()

    override fun onInitialize() {
        binding.let {
            it.btnEditDiaryCancel.setOnClickListener { finish() }
            it.btnEditDiaryOk.setOnClickListener { viewModel.commitDiary() }
            it.viewModel = viewModel
        }
        viewModel.let {
            it.attachDefaultHandlers(this)
            it.initializeByIntent(intent)
            it.eventUpdateComplete.observe(this) {
                finish()
            }
        }
    }

    companion object {
        const val EXTRA_INITIAL_DIARY = "initialDiary"
        const val EXTRA_DATE = "date"
        const val EXTRA_EMOTION_RES = "emotion"
    }
}