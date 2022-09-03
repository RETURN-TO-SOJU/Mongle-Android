package com.won983212.mongle.presentation.view.password

import android.content.Intent
import android.widget.RadioButton
import androidx.activity.viewModels
import androidx.core.view.children
import com.won983212.mongle.R
import com.won983212.mongle.common.util.toastShort
import com.won983212.mongle.databinding.ActivityPasswordBinding
import com.won983212.mongle.presentation.base.BaseDataActivity
import com.won983212.mongle.presentation.view.password.PasswordActivity.Companion.EXTRA_MODE
import com.won983212.mongle.presentation.view.password.PasswordActivity.Companion.EXTRA_REDIRECT_INTENT
import com.won983212.mongle.presentation.view.password.PasswordActivity.Mode
import dagger.hilt.android.AndroidEntryPoint


/**
 * ## Extras
 * * **(선택)** [EXTRA_MODE]: [Mode] -
 * 패스워드 설정/입력 모드. 기본값은 [AUTH][Mode.AUTH]
 * * **(선택)** [EXTRA_REDIRECT_INTENT]: [Intent] -
 * 패스워드 인증 성공시 리다이렉션할 intent.
 * [EXTRA_MODE]가 [AUTH][Mode.AUTH]인 경우에만 사용할 수 있다.
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
    }

    private fun registerViewModelEvents() {
        viewModel.eventAuthFinish.observe(this) {
            it?.let {
                startActivity(it)
            }
            setResult(RESULT_OK)
            finish()
        }

        viewModel.eventFail.observe(this) {
            toastShort(it)
        }

        viewModel.eventPwdIndicatorStateChanged.observe(this) {
            val state = it.second
            if (it.first == PasswordViewModel.INDICATOR_INDEX_ALL) {
                pwdIndicatorButtons.forEach { it.isChecked = state }
            } else {
                pwdIndicatorButtons[it.first].isChecked = state
            }
        }
    }

    override fun onBackPressed() {
        finish()
    }

    enum class Mode {
        SET, REENTER, AUTH
    }

    companion object {
        const val EXTRA_MODE = "mode"
        const val EXTRA_REDIRECT_INTENT = "redirect"
    }
}