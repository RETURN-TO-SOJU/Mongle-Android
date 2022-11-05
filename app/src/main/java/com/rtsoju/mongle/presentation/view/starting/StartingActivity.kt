package com.rtsoju.mongle.presentation.view.starting

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
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
    private lateinit var appUpdateManager: AppUpdateManager

    private fun makePasswordScreenLauncher(): ActivityResultLauncher<Intent> {
        return registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                loginFlow.launch()
            } else {
                finish()
            }
        }
    }

    private fun makeLoginFlow() {
        loginFlow = LoginFlow(this) {
            if (it == LoginFlow.LoginResult.REGISTER || it == LoginFlow.LoginResult.LOGIN) {
                Intent(applicationContext, MainActivity::class.java).apply {
                    putExtra(MainActivity.EXTRA_SHOW_SHOWCASE, it == LoginFlow.LoginResult.REGISTER)
                    startActivity(this)
                }
            }
            finish()
        }
    }

    private fun checkUpdate() {
        appUpdateManager.appUpdateInfo.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
            ) {
                appUpdateManager.startUpdateFlowForResult(
                    appUpdateInfo,
                    AppUpdateType.IMMEDIATE,
                    this,
                    REQUEST_APP_UPDATE
                )
            } else {
                launchLoginScreen()
            }
        }
    }

    private fun launchLoginScreen() {
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

        appUpdateManager = AppUpdateManagerFactory.create(this)
        checkUpdate()
        makeLoginFlow()
    }

    override fun onResume() {
        super.onResume()
        appUpdateManager.appUpdateInfo.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                appUpdateManager.startUpdateFlowForResult(
                    appUpdateInfo,
                    AppUpdateType.IMMEDIATE,
                    this,
                    REQUEST_APP_UPDATE
                )
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_APP_UPDATE) {
            if (resultCode != RESULT_OK) {
                finish()
            }
        }
    }

    companion object {
        private const val REQUEST_APP_UPDATE = 101
    }
}