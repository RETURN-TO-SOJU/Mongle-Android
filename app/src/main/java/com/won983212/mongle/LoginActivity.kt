package com.won983212.mongle

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.won983212.mongle.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private var keepSplash: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        val binding = ActivityLoginBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        applySplashLazyOut(splashScreen)
        initWidgets(binding)
    }

    private fun applySplashLazyOut(splashScreen: SplashScreen) {
        splashScreen.setKeepOnScreenCondition { keepSplash }
        Handler(Looper.getMainLooper())
            .postDelayed({ keepSplash = false }, 1000)
    }

    private fun initWidgets(binding: ActivityLoginBinding) {
        binding.loginWithKakaoButton.setOnClickListener {
            val uri = Uri.parse(
                "https://kauth.kakao.com/oauth/authorize?client_id=ccbac5ca9c6a907992ba6c1c10fd98d4" +
                        "&redirect_uri=http://localhost:8080/oauth2/kakao&response_type=code"
            )
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
    }
}