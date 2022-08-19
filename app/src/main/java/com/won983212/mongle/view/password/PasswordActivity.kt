package com.won983212.mongle.view.password

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.won983212.mongle.R
import com.won983212.mongle.databinding.ActivityPasswordBinding
import com.won983212.mongle.repository.PasswordRepository
import com.won983212.mongle.view.password.PasswordActivity.Companion.EXTRA_MODE
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

enum class PasswordActivityMode {
    SET, REENTER, AUTH
}

/**
 * ## Extras
 * * **(선택)** [EXTRA_MODE]: [PasswordActivityMode] -
 * 페스워드 설정/입력 모드. 기본값은 [AUTH][PasswordActivityMode.AUTH]
 */
@AndroidEntryPoint
class PasswordActivity : AppCompatActivity(), View.OnClickListener, PasswordInputListener {
    @Inject
    lateinit var passwordRepository: PasswordRepository

    private lateinit var pwdIndicatorButtons: Array<RadioButton>
    private lateinit var binding: ActivityPasswordBinding
    private var pwdPrevInput: String = ""
    private val pwdMemory = PasswordMemory(4)
    private var mode = PasswordActivityMode.AUTH

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarPassword)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        mode = (intent.getSerializableExtra(EXTRA_MODE)
            ?: PasswordActivityMode.AUTH) as PasswordActivityMode

        pwdIndicatorButtons = arrayOf(
            binding.btnPassword1,
            binding.btnPassword2,
            binding.btnPassword3,
            binding.btnPassword4
        )

        setupUIByMode()
        initEvents()
    }

    private fun setupUIByMode() {
        when (mode) {
            PasswordActivityMode.SET -> {
                binding.textPasswordPwdTitle.text = resources.getString(R.string.pwd_set_title)
                binding.textPasswordPwdSubtitle.text =
                    resources.getString(R.string.pwd_set_subtitle)
                binding.btnPasswordLostPwd.visibility = View.GONE
            }
            PasswordActivityMode.REENTER -> {
                binding.textPasswordPwdTitle.text = resources.getString(R.string.pwd_reenter_title)
                binding.textPasswordPwdSubtitle.text =
                    resources.getString(R.string.pwd_reenter_subtitle)
            }
            PasswordActivityMode.AUTH -> {
                binding.textPasswordPwdTitle.text = resources.getString(R.string.pwd_auth_title)
                binding.textPasswordPwdSubtitle.visibility = View.GONE
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
            PasswordActivityMode.AUTH -> {
                if (passwordRepository.getPassword() == password) {
                    setResult(RESULT_OK)
                    finish()
                } else {
                    Toast.makeText(this, R.string.pwd_wrong, Toast.LENGTH_SHORT).show()
                }
            }
            PasswordActivityMode.REENTER -> {
                if (pwdPrevInput == password) {
                    val result = Intent(this, PasswordActivity::class.java).apply {
                        putExtra(RESULT_PASSWORD, password)
                    }
                    passwordRepository.setPassword(password)
                    setResult(RESULT_OK, result)
                    finish()
                } else {
                    Toast.makeText(this, R.string.pwd_not_matched, Toast.LENGTH_SHORT).show()
                    mode = PasswordActivityMode.SET
                    setupUIByMode()
                }
            }
            PasswordActivityMode.SET -> {
                pwdPrevInput = password
                mode = PasswordActivityMode.REENTER
                setupUIByMode()
            }
        }
    }

    companion object {
        const val EXTRA_MODE = "mode"
        const val RESULT_PASSWORD = "password"
    }
}