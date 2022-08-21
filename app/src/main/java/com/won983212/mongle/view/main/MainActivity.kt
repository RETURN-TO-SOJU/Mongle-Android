package com.won983212.mongle.view.main

import android.content.Intent
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.won983212.mongle.R
import com.won983212.mongle.common.base.BaseDataActivity
import com.won983212.mongle.databinding.ActivityOverviewBinding
import com.won983212.mongle.view.daydetail.DayDetailActivity
import com.won983212.mongle.view.tutorial.TutorialActivity

class MainActivity : BaseDataActivity<ActivityOverviewBinding>() {
    private val viewModel by viewModels<MainViewModel>()

    override val layoutId: Int = R.layout.activity_main

    override fun onInitialize() {
        binding.viewModel = viewModel
        setSupportActionBar(binding.toolbarOverview)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        viewModel.calendarEmotions.observe(this) {
            binding.calendarOverview.setDayEmotions(it)
        }

        binding.btnOverviewTutorialKakaoExport.setOnClickListener {
            TutorialActivity.startKakaoTutorial(this)
        }

        binding.btnOverviewShowDetail.setOnClickListener {
            // TODO add extra today data
            Intent(this, DayDetailActivity::class.java).apply {
                startActivity(this)
            }
        }

        initKeywordList()
    }

    private fun initKeywordList() {
        binding.listKeyword.apply {
            layoutManager = LinearLayoutManager(context).apply {
                orientation = LinearLayoutManager.HORIZONTAL
            }
            adapter = KeywordAdapter()
        }
    }
}