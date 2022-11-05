package com.rtsoju.mongle.presentation.view.login

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.rtsoju.mongle.presentation.util.getSerializableExtraCompat
import com.rtsoju.mongle.presentation.view.agree.AgreeActivity
import com.rtsoju.mongle.presentation.view.login.LoginFlow.LoginResult
import com.rtsoju.mongle.presentation.view.setname.SetNameActivity
import com.rtsoju.mongle.presentation.view.tutorial.TutorialActivity

/**
 * Login Activity에서 필요한 Activity 전이 작업을 해주는 클래스.
 * Login시 가입이 필요한 경우 가입 플로우(Agree -> SetName -> Tutorial),
 * 아닌 경우에는 플로우가 바로 종료된다.
 * **반드시 [activity]가 STARTED상태에 진입하기 이전에 LoginFlow를 생성해야한다.**
 * 그 이후에 생성할 경우에는 예외발생.
 * 최종적으로 가입하고 로그인 한 상태이면 [LoginResult.REGISTER],
 * 로그인만 한 상태라면 [LoginResult.LOGIN],
 * 도중에 취소한 경우에는 [LoginResult.CANCELLED]를 [listener]을 통해 반환한다.
 */
class LoginFlow(
    private val activity: ComponentActivity,
    private val listener: LoginResultListener
) {
    private var loginState: LoginResult = LoginResult.CANCELLED
    private val loginLauncher: ActivityResultLauncher<Intent>

    init {
        val setNameLauncher = registerSetNameResult()
        val agreeLauncher = registerAgreeResult(setNameLauncher)
        loginLauncher = registerLoginResult(agreeLauncher)
    }

    private fun registerSetNameResult(): ActivityResultLauncher<Intent> {
        return activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == AppCompatActivity.RESULT_OK) {
                listener.onComplete(loginState)
                TutorialActivity.startSecurityTutorial(activity)
            } else {
                listener.onComplete(LoginResult.CANCELLED)
            }
        }
    }

    private fun registerAgreeResult(setNameLauncher: ActivityResultLauncher<Intent>): ActivityResultLauncher<Intent> {
        return activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == AppCompatActivity.RESULT_OK) {
                setNameLauncher.launch(
                    Intent(
                        activity.applicationContext,
                        SetNameActivity::class.java
                    )
                )
            } else {
                listener.onComplete(LoginResult.CANCELLED)
            }
        }
    }

    private fun registerLoginResult(agreeLauncher: ActivityResultLauncher<Intent>): ActivityResultLauncher<Intent> {
        return activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            loginState =
                if (it.resultCode == AppCompatActivity.RESULT_OK) {
                    it.data?.getSerializableExtraCompat(LoginActivity.RESULT_LOGIN)
                        ?: LoginResult.CANCELLED
                } else {
                    LoginResult.CANCELLED
                }
            when (loginState) {
                LoginResult.LOGIN, LoginResult.CANCELLED -> {
                    listener.onComplete(loginState)
                }
                LoginResult.REGISTER -> {
                    agreeLauncher.launch(
                        Intent(
                            activity.applicationContext,
                            AgreeActivity::class.java
                        )
                    )
                }
            }
        }
    }

    fun launch() {
        loginLauncher.launch(
            Intent(
                activity.applicationContext,
                LoginActivity::class.java
            )
        )
    }

    enum class LoginResult {
        /** 사용자가 회원가입을 한 경우 */
        REGISTER,

        /** 사용자가 로그인을 한 경우 (기존 회원이라면 회원가입 절차없이 로그인) */
        LOGIN,

        /** 사용자가 로그인을 거부한 경우 (뒤로가기, 종료 등) */
        CANCELLED
    }

    fun interface LoginResultListener {
        fun onComplete(result: LoginResult)
    }
}