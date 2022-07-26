package com.won983212.mongle

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

class LoginActivity : AppCompatActivity() {
    private var keepSplash: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        splashScreen.setKeepOnScreenCondition { keepSplash }
        Handler(Looper.getMainLooper())
            .postDelayed({ keepSplash = false }, 1000)
    }
}