package com.won983212.mongle.view.overview

import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.won983212.mongle.R
import com.won983212.mongle.common.base.BaseDataActivity
import com.won983212.mongle.databinding.ActivityOverviewBinding

class OverviewActivity : BaseDataActivity<ActivityOverviewBinding>() {
    private val viewModel by viewModels<OverviewViewModel>()

    override val layoutId: Int = R.layout.activity_overview

    override fun onInitialize() {
        binding.viewModel = viewModel
        setSupportActionBar(binding.toolbarOverview)
        supportActionBar?.setDisplayShowTitleEnabled(false)
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