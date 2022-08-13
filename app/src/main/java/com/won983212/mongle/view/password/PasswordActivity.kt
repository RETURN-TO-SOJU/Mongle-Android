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
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

enum class PasswordActivityMode {
    SET, REENTER, AUTH
}

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

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        mode = (intent.getSerializableExtra(MODE)
            ?: PasswordActivityMode.AUTH) as PasswordActivityMode

        pwdIndicatorButtons = arrayOf(
            binding.btnPwd1,
            binding.btnPwd2,
            binding.btnPwd3,
            binding.btnPwd4
        )

        setupUIByMode()
        initEvents()
    }

    private fun setupUIByMode() {
        when (mode) {
            PasswordActivityMode.SET -> {
                binding.textPwdTitle.text = resources.getString(R.string.pwd_set_title)
                binding.textPwdSubtitle.text = resources.getString(R.string.pwd_set_subtitle)
                binding.btnLostPwd.visibility = View.GONE
            }
            PasswordActivityMode.REENTER -> {
                binding.textPwdTitle.text = resources.getString(R.string.pwd_reenter_title)
                binding.textPwdSubtitle.text = resources.getString(R.string.pwd_reenter_subtitle)
            }
            PasswordActivityMode.AUTH -> {
                binding.textPwdTitle.text = resources.getString(R.string.pwd_auth_title)
                binding.textPwdSubtitle.visibility = View.GONE
            }
        }
    }

    private fun initEvents() {
        pwdMemory.setOnFullListener(this)
        arrayOf(
            binding.btnNumpad0,
            binding.btnNumpad1,
            binding.btnNumpad2,
            binding.btnNumpad3,
            binding.btnNumpad4,
            binding.btnNumpad5,
            binding.btnNumpad6,
            binding.btnNumpad7,
            binding.btnNumpad8,
            binding.btnNumpad9,
            binding.btnNumpadDelete
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
        const val MODE = "mode"
        const val RESULT_PASSWORD = "password"
    }
}