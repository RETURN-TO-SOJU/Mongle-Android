package com.won983212.mongle.presentation.view.password

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.won983212.mongle.R
import com.won983212.mongle.databinding.ActivityPasswordBinding
import com.won983212.mongle.presentation.view.password.PasswordActivity.Companion.EXTRA_MODE
import com.won983212.mongle.presentation.view.password.PasswordActivity.Companion.EXTRA_REDIRECT_INTENT
import com.won983212.mongle.presentation.view.password.PasswordActivity.Mode
import dagger.hilt.android.AndroidEntryPoint


/**
 * TODO refactor with Viewmodel
 * ## Extras
 * * **(선택)** [EXTRA_MODE]: [Mode] -
 * 패스워드 설정/입력 모드. 기본값은 [AUTH][Mode.AUTH]
 * * **(선택)** [EXTRA_REDIRECT_INTENT]: [Intent] -
 * 패스워드 인증 성공시 리다이렉션할 intent.
 * [EXTRA_MODE]가 [AUTH][Mode.AUTH]인 경우에만 사용할 수 있다.
 */
@AndroidEntryPoint
class PasswordActivity : AppCompatActivity(), View.OnClickListener, PasswordInputListener {

    private val viewModel by viewModels<PasswordViewModel>()
    private lateinit var pwdIndicatorButtons: Array<RadioButton>
    private lateinit var binding: ActivityPasswordBinding
    private var pwdPrevInput: String = ""
    private val pwdMemory = PasswordMemory(4)
    private var mode = Mode.AUTH
    private var redirectTo: Intent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarPassword)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        mode = (intent.getSerializableExtra(EXTRA_MODE)
            ?: Mode.AUTH) as Mode

        redirectTo = intent.getParcelableExtra(EXTRA_REDIRECT_INTENT)

        pwdIndicatorButtons = arrayOf(
            binding.btnPassword1,
            binding.btnPassword2,
            binding.btnPassword3,
            binding.btnPassword4
        )

        setupUIByMode()
        initEvents()
    }

    override fun onBackPressed() {
        finish()
    }

    private fun setupUIByMode() {
        when (mode) {
            Mode.SET -> {
                binding.textPasswordPwdTitle.text = resources.getString(R.string.pwd_set_title)
                binding.textPasswordPwdSubtitle.text =
                    resources.getString(R.string.pwd_set_subtitle)
                binding.btnPasswordLostPwd.text = resources.getText(R.string.pwd_delete)
                binding.btnPasswordLostPwd.setOnClickListener {
                    viewModel.setPassword(null)
                    setResult(RESULT_OK, null)
                    finish()
                }
            }
            Mode.REENTER -> {
                binding.textPasswordPwdTitle.text = resources.getString(R.string.pwd_reenter_title)
                binding.textPasswordPwdSubtitle.text =
                    resources.getString(R.string.pwd_reenter_subtitle)
            }
            Mode.AUTH -> {
                binding.textPasswordPwdTitle.text = resources.getString(R.string.pwd_auth_title)
                binding.textPasswordPwdSubtitle.visibility = View.GONE

                // TODO 암호분실 기능은 향후에 구현
                binding.btnPasswordLostPwd.visibility = View.GONE
                binding.btnPasswordLostPwd.text = resources.getText(R.string.pwd_lost)
            }
        }
    }

    private fun initEvents() {
        pwdMemory.setOnFullListener(this)
        arrayOf(
            binding.btnPasswordNumpad0,
            binding.btnPasswordNumpad1,
            binding.btnPasswordNumpad2,
            binding.btnPasswordNumpad3,
            binding.btnPasswordNumpad4,
            binding.btnPasswordNumpad5,
            binding.btnPasswordNumpad6,
            binding.btnPasswordNumpad7,
            binding.btnPasswordNumpad8,
            binding.btnPasswordNumpad9,
            binding.btnPasswordNumpadDelete
        ).forEach { it.setOnClickListener(this) }
    }

    private fun appendPassword(digit: Char) {
        val length = pwdMemory.pushDigit(digit)
        if (length > 0) {
            pwdIndicatorButtons[length - 1].isChecked = true
        } else {
            pwdIndicatorButtons.forEach { it.isChecked = false }
        }
    }

    private fun removePassword() {
        val length = pwdMemory.popDigit()
        pwdIndicatorButtons[length].isChecked = false
    }

    override fun onClick(view: View?) {
        if (view is Button) {
            appendPassword(view.text[0])
        } else if (view is ImageButton) {
            removePassword()
        }
    }

    override fun onPasswordInput(password: String) {
        when (mode) {
            Mode.AUTH -> {
                if (viewModel.checkPassword(password)) {
                    setResult(RESULT_OK)
                    redirectTo?.let {
                        startActivity(it)
                    }
                    finish()
                } else {
                    Toast.makeText(this, R.string.pwd_wrong, Toast.LENGTH_SHORT).show()
                }
            }
            Mode.REENTER -> {
                if (pwdPrevInput == password) {
                    val result = Intent(this, PasswordActivity::class.java).apply {
                        putExtra(RESULT_PASSWORD, password)
                    }
                    viewModel.setPassword(password)
                    setResult(RESULT_OK, result)
                    finish()
                } else {
                    Toast.makeText(this, R.string.pwd_not_matched, Toast.LENGTH_SHORT).show()
                    mode = Mode.SET
                    setupUIByMode()
                }
            }
            Mode.SET -> {
                pwdPrevInput = password
                mode = Mode.REENTER
                setupUIByMode()
            }
        }
    }

    enum class Mode {
        SET, REENTER, AUTH
    }

    companion object {
        const val EXTRA_MODE = "mode"
        const val EXTRA_REDIRECT_INTENT = "redirect"
        const val RESULT_PASSWORD = "password"
    }
}