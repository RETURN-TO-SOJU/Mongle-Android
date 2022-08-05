package com.won983212.mongle

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.kakao.sdk.user.UserApiClient
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
            val client = UserApiClient.instance
            if (!client.isKakaoTalkLoginAvailable(this)) {
                UserApiClient.instance.loginWithKakaoAccount(this) { token, error ->
                    if (error != null) {
                        Log.e("KakaoLogin", error.toString())
                        Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
                    } else if (token != null) {
                        Toast.makeText(this, "로그인 성공 ${token.accessToken}", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            } else {
                client.loginWithKakaoTalk(this) { token, error ->
                    if (error != null) {
                        Log.e("KakaoLogin", error.toString())
                        Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
                    } else if (token != null) {
                        Toast.makeText(this, "로그인 성공 ${token.accessToken}", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }
}