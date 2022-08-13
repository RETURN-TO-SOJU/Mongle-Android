package com.won983212.mongle.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.won983212.mongle.databinding.ActivityEditDiaryBinding

class EditDiaryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityEditDiaryBinding.inflate(layoutInflater)
        binding.btnCancel.setOnClickListener { finish() }
        binding.edittextDiary.setText(intent.getStringExtra(EXTRA_INITIAL_DIARY) ?: "")

        setContentView(binding.root)
    }

    companion object {
        const val EXTRA_INITIAL_DIARY = "initialDiary"
    }
}