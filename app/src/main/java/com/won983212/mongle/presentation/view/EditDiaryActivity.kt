package com.won983212.mongle.presentation.view

import android.os.Bundle
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import com.won983212.mongle.R
import com.won983212.mongle.databinding.ActivityEditDiaryBinding
import com.won983212.mongle.presentation.view.EditDiaryActivity.Companion.EXTRA_EMOTION
import com.won983212.mongle.presentation.view.EditDiaryActivity.Companion.EXTRA_INITIAL_DIARY
import com.won983212.mongle.presentation.view.EditDiaryActivity.Companion.EXTRA_TITLE

/**
 * ## Extras
 * * **(선택)** [EXTRA_TITLE]: [String] -
 * 일기장 상단에 표시될 제목. 기본값은 empty string
 * * **(선택)** [EXTRA_INITIAL_DIARY]: [String] -
 * 초기 일기장 텍스트. 기본값은 empty string
 * * **(선택)** [EXTRA_EMOTION]: [DrawableRes] -
 * 일기장에 표시될 감정 Icon Resource. 기본값은 @drawable/ic_edit
 */
class EditDiaryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityEditDiaryBinding.inflate(layoutInflater)
        binding.btnEditDiaryCancel.setOnClickListener { finish() }
        binding.imageEditDiaryEmotion.setImageResource(
            intent.getIntExtra(
                EXTRA_EMOTION,
                R.drawable.ic_edit
            )
        )
        binding.textEditDiaryTitle.text = intent.getStringExtra(EXTRA_TITLE) ?: ""
        binding.edittextEditDiary.setText(intent.getStringExtra(EXTRA_INITIAL_DIARY) ?: "")

        setContentView(binding.root)
    }

    companion object {
        const val EXTRA_INITIAL_DIARY = "initialDiary"
        const val EXTRA_TITLE = "title"
        const val EXTRA_EMOTION = "emotion"
    }
}