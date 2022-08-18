package com.won983212.mongle.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.won983212.mongle.databinding.ActivityLoginBinding
import com.won983212.mongle.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            viewModel.loginWithKakao()
        }

        viewModel.eventLoginKakao.observe(this) {
            Intent(this, AgreeActivity::class.java).apply {
                startActivity(this)
            }
            finish()
        }
    }
}