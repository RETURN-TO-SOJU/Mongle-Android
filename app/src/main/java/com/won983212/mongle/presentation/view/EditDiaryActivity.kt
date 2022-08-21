package com.won983212.mongle.presentation.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.won983212.mongle.databinding.ActivityEditDiaryBinding

/**
 * ## Extras
 * * **(선택)** [EXTRA_INITIAL_DIARY]: [String] -
 * 초기 일기장 텍스트. 기본값은 Empty String
 */
class EditDiaryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityEditDiaryBinding.inflate(layoutInflater)
        binding.btnEditDiaryCancel.setOnClickListener { finish() }
        binding.edittextEditDiary.setText(intent.getStringExtra(EXTRA_INITIAL_DIARY) ?: "")

        setContentView(binding.root)
    }

    companion object {
        const val EXTRA_INITIAL_DIARY = "initialDiary"
    }
}