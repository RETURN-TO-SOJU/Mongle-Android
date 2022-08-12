package com.won983212.mongle.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.won983212.mongle.databinding.ActivityDayDetailBinding

class DayDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDayDetailBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        supportActionBar?.setDisplayShowTitleEnabled(false)
    }
}