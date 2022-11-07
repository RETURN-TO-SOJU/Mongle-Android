package com.rtsoju.mongle.presentation.view.main

import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.rtsoju.mongle.R
import com.rtsoju.mongle.databinding.ActivityMainBinding
import com.rtsoju.mongle.presentation.base.BaseDataActivity
import com.rtsoju.mongle.presentation.util.toastShort
import com.rtsoju.mongle.presentation.view.dialog.AnalyzeCompleteDialog
import com.rtsoju.mongle.presentation.view.dialog.InputPasswordDialog
import com.rtsoju.mongle.presentation.view.main.MainActivity.Companion.EXTRA_ANALYZED_DATE_RANGE
import com.rtsoju.mongle.presentation.view.main.MainActivity.Companion.EXTRA_SHOW_SHOWCASE
import dagger.hilt.android.AndroidEntryPoint

/**
 * ## Extras
 * * **(선택)** [EXTRA_ANALYZED_DATE_RANGE]: [String] -
 * Analyzed Complete Dialog를 띄우면서, 이 parameter를 date range로써 보여준다.
 * null일경우 dialog를 띄우지 않는다. null이 기본값이다
 * * **(선택)** [EXTRA_SHOW_SHOWCASE]: [Boolean] -
 * 카카오톡 분석해보라는 간단한 showcase를 보여준다.
 * 기본값은 false이고, true로 설정하면 showcase를 보여준다.
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
            AnalyzeCompleteDialog(
                this,
                dateText
            ).open()
        }

        viewModel.setShowShowcase(intent.getBooleanExtra(EXTRA_SHOW_SHOWCASE, false))
        viewModel.checkIfPasswordEmpty()
        viewModel.eventEmptyPassword.observe(this) {
            InputPasswordDialog(this)
                .setOnSubmitPassword {
                    viewModel.setPasswordTo(it)
                    toastShort("암호키 비밀번호가 설정되었습니다.")
                }
                .open()
        }
    }

    private fun setupNavigation() {
        val host = supportFragmentManager
            .findFragmentById(R.id.container_main_nav_host) as NavHostFragment? ?: return
        binding.navMainBottom.setupWithNavController(host.navController)
    }

    companion object {
        const val EXTRA_ANALYZED_DATE_RANGE = "analyzedDateRange"
        const val EXTRA_SHOW_SHOWCASE = "showTutorial"
    }
}