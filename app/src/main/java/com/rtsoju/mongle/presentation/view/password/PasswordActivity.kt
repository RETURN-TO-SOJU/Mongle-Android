package com.rtsoju.mongle.presentation.view.password

import android.widget.RadioButton
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.core.view.children
import com.rtsoju.mongle.R
import com.rtsoju.mongle.databinding.ActivityPasswordBinding
import com.rtsoju.mongle.presentation.base.BaseDataActivity
import com.rtsoju.mongle.presentation.util.toastShort
import com.rtsoju.mongle.presentation.view.password.PasswordActivity.Companion.EXTRA_MODE
import com.rtsoju.mongle.presentation.view.password.PasswordActivity.Mode
import dagger.hilt.android.AndroidEntryPoint


/**
 * ## Extras
 * * **(선택)** [EXTRA_MODE]: [Mode] -
 * 패스워드 설정/입력 모드. 기본값은 [AUTH][Mode.AUTH]
 */
@AndroidEntryPoint
class PasswordActivity : BaseDataActivity<ActivityPasswordBinding>() {

    override val layoutId: Int = R.layout.activity_password
    private val viewModel by viewModels<PasswordViewModel>()
    private lateinit var pwdIndicatorButtons: List<RadioButton>

    override fun onInitialize() {
        binding.viewModel = viewModel
        setSupportActionBar(binding.toolbarPassword)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        pwdIndicatorButtons =
            binding.layoutPasswordIndicator.children.map { it as RadioButton }.toList()

        viewModel.initializeByIntent(intent)
        registerViewModelEvents()

        onBackPressedDispatcher.addCallback(this) {
            finish()
        }
    }

    private fun registerViewModelEvents() {
        viewModel.eventAuthFinish.observe(this) {
            setResult(RESULT_OK)
            finish()
        }

        viewModel.eventFail.observe(this) {
            toastShort(it)
        }

        viewModel.eventPwdIndicatorStateChanged.observe(this) { it ->
            val state = it.second
            if (it.first == PasswordViewModel.INDICATOR_INDEX_ALL) {
                pwdIndicatorButtons.forEach { it.isChecked = state }
            } else {
                pwdIndicatorButtons[it.first].isChecked = state
            }
        }
    }

    enum class Mode {
        SET, REENTER, AUTH
    }

    companion object {
        const val EXTRA_MODE = "mode"
    }
}