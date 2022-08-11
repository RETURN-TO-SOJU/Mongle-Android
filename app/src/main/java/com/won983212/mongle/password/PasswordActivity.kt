package com.won983212.mongle.password

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import com.won983212.mongle.databinding.ActivityPasswordBinding

class PasswordActivity : AppCompatActivity(), View.OnClickListener, PasswordFullListener {
    private lateinit var pwdIndicatorButtons: Array<RadioButton>
    private val passwordRepository = PasswordRepository(4)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pwdIndicatorButtons = arrayOf(
            binding.pwdBtn1,
            binding.pwdBtn2,
            binding.pwdBtn3,
            binding.pwdBtn4
        )

        passwordRepository.setOnFullListener(this)
        initEvents(binding)
    }

    private fun initEvents(binding: ActivityPasswordBinding) {
        arrayOf(
            binding.pwdIn0,
            binding.pwdIn1,
            binding.pwdIn2,
            binding.pwdIn3,
            binding.pwdIn4,
            binding.pwdIn5,
            binding.pwdIn6,
            binding.pwdIn7,
            binding.pwdIn8,
            binding.pwdIn9,
            binding.pwdInDelete
        ).forEach { it.setOnClickListener(this) }
    }

    private fun appendPassword(digit: Char) {
        val length = passwordRepository.pushDigit(digit)
        if (length > 0) {
            pwdIndicatorButtons[length - 1].isChecked = true
        } else {
            pwdIndicatorButtons.forEach { it.isChecked = false }
        }
    }

    private fun removePassword() {
        val length = passwordRepository.popDigit()
        pwdIndicatorButtons[length].isChecked = false
    }

    override fun onClick(view: View?) {
        if (view is Button) {
            appendPassword(view.text[0])
        } else if (view is ImageButton) {
            removePassword()
        }
    }

    override fun onFull(password: String) {
        // TODO Implement match password
    }
}