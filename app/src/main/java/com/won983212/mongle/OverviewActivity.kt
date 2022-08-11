package com.won983212.mongle

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.won983212.mongle.databinding.ActivityOverviewBinding

class OverviewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityOverviewBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        supportActionBar?.setDisplayShowTitleEnabled(false)
    }
}