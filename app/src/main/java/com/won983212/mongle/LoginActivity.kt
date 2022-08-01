package com.won983212.mongle

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.won983212.mongle.databinding.ActivityLoginBinding
import com.won983212.mongle.password.PasswordActivity

class LoginActivity : AppCompatActivity() {
    private var keepSplash: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        val binding = ActivityLoginBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        applySplashLazyOut(splashScreen)
        attachEvents(binding)
    }

    private fun applySplashLazyOut(splashScreen: SplashScreen) {
        splashScreen.setKeepOnScreenCondition { keepSplash }
        Handler(Looper.getMainLooper())
            .postDelayed({ keepSplash = false }, 1000)
    }

    private fun attachEvents(binding: ActivityLoginBinding) {
        binding.loginWithKakaoButton.setOnClickListener {
            val intent = Intent(this, PasswordActivity::class.java)
            startActivity(intent)
        }
    }
}