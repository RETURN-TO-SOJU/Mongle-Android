package com.rtsoju.mongle.presentation.view.login

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.kakao.sdk.user.UserApiClient
import com.rtsoju.mongle.R
import com.rtsoju.mongle.databinding.ActivityLoginBinding
import com.rtsoju.mongle.presentation.base.BaseDataActivity
import com.rtsoju.mongle.presentation.util.toastLong
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginActivity : BaseDataActivity<ActivityLoginBinding>() {

    private val viewModel by viewModels<LoginViewModel>()
    override val layoutId: Int = R.layout.activity_login

    override fun onInitialize() {
        binding.viewModel = viewModel

        binding.btnLogin.setOnClickListener {
            loginWithKakao()
        }

        viewModel.attachDefaultHandlers(this)
        viewModel.eventReadyForRegister.observe(this) {
            setLoginResult(LoginFlow.LoginResult.REGISTER)
            finish()
        }

        viewModel.eventLoggedIn.observe(this) {
            setLoginResult(LoginFlow.LoginResult.LOGIN)
            finish()
        }

        viewModel.checkCanAutoLogin()
    }

    /**
     * Kakao Login은 Activity Context가 필요하므로 ViewModel에서 처리할 수 없음.
     */
    private fun loginWithKakao() {
        val client = UserApiClient.instance
        if (!client.isKakaoTalkLoginAvailable(this)) {
            toastLong("카카오톡이 설치되어있지 않거나, 카카오톡 로그인이 지원되지 않습니다.")
        } else {
            client.loginWithKakaoTalk(this) { token, error ->
                if (error != null) {
                    Log.e("KakaoLogin", error.toString())
                    Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
                } else if (token != null) {
                    viewModel.doLoginWithKakaoToken(token)
                }
            }
        }
    }

    private fun setLoginResult(result: LoginFlow.LoginResult) {
        setResult(RESULT_OK, Intent().putExtra(RESULT_LOGIN, result))
    }

    companion object {
        const val RESULT_LOGIN = "loginResult"
    }
}