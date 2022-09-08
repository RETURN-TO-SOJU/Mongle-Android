package com.won983212.mongle.presentation.view.namesetup

import android.content.Intent
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.won983212.mongle.R
import com.won983212.mongle.databinding.ActivityNameSettingBinding
import com.won983212.mongle.presentation.base.BaseDataActivity
import com.won983212.mongle.presentation.view.agree.AgreeActivity.Companion.EXTRA_REDIRECT_TO
import com.won983212.mongle.presentation.view.login.LoginActivity
import com.won983212.mongle.presentation.view.login.LoginActivity.Companion.EXTRA_REDIRECT_TO
import com.won983212.mongle.presentation.view.namesetup.NameSettingActivity.Companion.EXTRA_REDIRECT_TO
import com.won983212.mongle.presentation.view.tutorial.TutorialActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * ## Extras
 * * **(선택)** [EXTRA_REDIRECT_TO]: [Intent] -
 * NameSetting과정이 끝나면, 지정한 intent를 startActivity
 */
@AndroidEntryPoint
class NameSettingActivity : BaseDataActivity<ActivityNameSettingBinding>() {

    override val layoutId: Int = R.layout.activity_name_setting
    private val viewModel by viewModels<NameSettingViewModel>()

    override fun onInitialize() {
        binding.viewModel = viewModel

        val redirectTo = intent.getParcelableExtra(LoginActivity.EXTRA_REDIRECT_TO) as? Intent
        binding.btnNameSettingOk.setOnClickListener {
            lifecycleScope.launch {
                viewModel.updateUsername()
                withContext(Dispatchers.Main) {
                    if (redirectTo != null) {
                        startActivity(redirectTo)
                    }
                    TutorialActivity.startKakaoTutorial(this@NameSettingActivity)
                    finish()
                }
            }
        }
    }

    companion object {
        const val EXTRA_REDIRECT_TO = "redirectTo"
    }
}