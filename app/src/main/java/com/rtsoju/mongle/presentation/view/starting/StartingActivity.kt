package com.rtsoju.mongle.presentation.view.starting

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.rtsoju.mongle.R
import com.rtsoju.mongle.USE_TEST_ACTIVITY
import com.rtsoju.mongle.debug.view.MainTestActivity
import com.rtsoju.mongle.presentation.base.BaseActivity
import com.rtsoju.mongle.presentation.view.login.LoginFlow
import com.rtsoju.mongle.presentation.view.main.MainActivity
import com.rtsoju.mongle.presentation.view.password.PasswordActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StartingActivity : BaseActivity() {
    private val viewModel by viewModels<StartingViewModel>()
    private lateinit var loginFlow: LoginFlow

    private fun makePasswordScreenLauncher(): ActivityResultLauncher<Intent> {
        return registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                loginFlow.launch()
            } else {
                finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_starting)

        if (USE_TEST_ACTIVITY) {
            Intent(applicationContext, MainTestActivity::class.java).apply {
                startActivity(this)
            }
            return
        }

        loginFlow = LoginFlow(this) {
            if (it == LoginFlow.LoginResult.REGISTER || it == LoginFlow.LoginResult.LOGIN) {
                Intent(applicationContext, MainActivity::class.java).apply {
                    putExtra(MainActivity.EXTRA_SHOW_SHOWCASE, it == LoginFlow.LoginResult.REGISTER)
                    startActivity(this)
                }
            }
            finish()
        }

        if (viewModel.needsPasswordAuth()) {
            makePasswordScreenLauncher().launch(
                Intent(applicationContext, PasswordActivity::class.java).apply {
                    putExtra(PasswordActivity.EXTRA_MODE, PasswordActivity.Mode.AUTH)
                }
            )
        } else {
            loginFlow.launch()
        }
    }
}