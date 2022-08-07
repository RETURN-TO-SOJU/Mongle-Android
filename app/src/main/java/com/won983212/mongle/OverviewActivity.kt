package com.won983212.mongle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.won983212.mongle.databinding.ActivityOverviewBinding

class OverviewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_overview)

        val binding = ActivityOverviewBinding.inflate(layoutInflater)
        setSupportActionBar(binding.toolbar)
    }
}