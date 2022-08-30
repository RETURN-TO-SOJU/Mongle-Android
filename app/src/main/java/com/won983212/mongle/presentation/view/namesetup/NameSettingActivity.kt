package com.won983212.mongle.presentation.view.namesetup

import android.content.Intent
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.won983212.mongle.R
import com.won983212.mongle.databinding.ActivityNameSettingBinding
import com.won983212.mongle.presentation.base.BaseDataActivity
import com.won983212.mongle.presentation.view.main.MainActivity
import com.won983212.mongle.presentation.view.tutorial.TutorialActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class NameSettingActivity : BaseDataActivity<ActivityNameSettingBinding>() {

    override val layoutId: Int = R.layout.activity_name_setting
    private val viewModel by viewModels<NameSettingViewModel>()

    override fun onInitialize() {
        binding.viewModel = viewModel
        binding.btnNameSettingOk.setOnClickListener {
            lifecycleScope.launch {
                viewModel.updateUsername()
                withContext(Dispatchers.Main) {
                    Intent(this@NameSettingActivity, MainActivity::class.java).apply {
                        startActivity(this)
                    }
                    TutorialActivity.startKakaoTutorial(this@NameSettingActivity)
                    finish()
                }
            }
        }
    }
}