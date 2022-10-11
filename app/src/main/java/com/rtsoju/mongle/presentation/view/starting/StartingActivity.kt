package com.rtsoju.mongle.presentation.view.starting

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.rtsoju.mongle.BuildConfig
import com.rtsoju.mongle.R
import com.rtsoju.mongle.debug.view.MainTestActivity
import com.rtsoju.mongle.presentation.base.BaseActivity
import com.rtsoju.mongle.presentation.view.login.LoginActivity
import com.rtsoju.mongle.presentation.view.main.MainActivity
import com.rtsoju.mongle.presentation.view.password.PasswordActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StartingActivity : BaseActivity() {
    private val viewModel by viewModels<StartingViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_starting)

        if (BuildConfig.USE_TEST_ACTIVITY) {
            Intent(applicationContext, MainTestActivity::class.java).apply {
                startActivity(this)
            }
            return
        }

        val loginIntent = Intent(applicationContext, LoginActivity::class.java).apply {
            putExtra(
                LoginActivity.EXTRA_REDIRECT_TO,
                Intent(applicationContext, MainActivity::class.java)
            )
        }

        if (viewModel.needsPasswordAuth()) {
            Intent(this, PasswordActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                putExtra(PasswordActivity.EXTRA_MODE, PasswordActivity.Mode.AUTH)
                putExtra(
                    PasswordActivity.EXTRA_REDIRECT_INTENT,
                    loginIntent
                )
                startActivity(this)
            }
        } else {
            startActivity(loginIntent)
            finish()
        }
    }
}