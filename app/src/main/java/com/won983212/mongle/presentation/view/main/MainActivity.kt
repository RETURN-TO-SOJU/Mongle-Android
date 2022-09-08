package com.won983212.mongle.presentation.view.main

import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.won983212.mongle.R
import com.won983212.mongle.databinding.ActivityMainBinding
import com.won983212.mongle.presentation.base.BaseDataActivity
import com.won983212.mongle.presentation.view.main.MainActivity.Companion.EXTRA_ANALYZED_DATE_RANGE
import com.won983212.mongle.presentation.view.openAnalyzeCompleteDialog
import dagger.hilt.android.AndroidEntryPoint

/**
 * ## Extras
 * * **(선택)** [EXTRA_ANALYZED_DATE_RANGE]: [String] -
 * Analyzed Complete Dialog를 띄우면서, 이 parameter를 date range로써 보여준다.
 * null일경우 dialog를 띄우지 않는다. null이 기본값이다
 */
@AndroidEntryPoint
class MainActivity : BaseDataActivity<ActivityMainBinding>() {
    private val viewModel by viewModels<MainViewModel>()
    override val layoutId: Int = R.layout.activity_main

    override fun onInitialize() {
        binding.viewModel = viewModel
        setupNavigation()

        val dateText = intent.getStringExtra(EXTRA_ANALYZED_DATE_RANGE)
        if (dateText != null) {
            openAnalyzeCompleteDialog(
                this,
                "",
                dateText
            )
        }
    }

    private fun setupNavigation() {
        val host = supportFragmentManager
            .findFragmentById(R.id.container_main_nav_host) as NavHostFragment? ?: return
        binding.navMainBottom.setupWithNavController(host.navController)
    }

    companion object {
        const val EXTRA_ANALYZED_DATE_RANGE = "analyzedDateRange"
    }
}