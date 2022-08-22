package com.won983212.mongle.presentation.view.diary

import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import com.won983212.mongle.R
import com.won983212.mongle.databinding.ActivityEditDiaryBinding
import com.won983212.mongle.presentation.base.BaseDataActivity
import com.won983212.mongle.presentation.view.diary.EditDiaryActivity.Companion.EXTRA_DATE
import com.won983212.mongle.presentation.view.diary.EditDiaryActivity.Companion.EXTRA_EMOTION
import com.won983212.mongle.presentation.view.diary.EditDiaryActivity.Companion.EXTRA_INITIAL_DIARY
import dagger.hilt.android.AndroidEntryPoint

/**
 * ## Extras
 * * **(선택)** [EXTRA_DATE]: [String] -
 * 일기장 상단에 표시될 제목. 기본값은 empty string
 * * **(선택)** [EXTRA_INITIAL_DIARY]: [String] -
 * 초기 일기장 텍스트. 기본값은 empty string
 * * **(선택)** [EXTRA_EMOTION]: [DrawableRes] -
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
            it.attachDefaultErrorHandler(this)
            it.attachDefaultLoadingHandler(this)
            it.initialzeFromIntent(intent)
            it.eventUpdateComplete.observe(this) {
                finish()
            }
        }
    }

    companion object {
        const val EXTRA_INITIAL_DIARY = "initialDiary"
        const val EXTRA_DATE = "date"
        const val EXTRA_EMOTION = "emotion"
    }
}