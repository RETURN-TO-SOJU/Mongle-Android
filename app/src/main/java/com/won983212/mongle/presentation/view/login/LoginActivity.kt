package com.won983212.mongle.presentation.view.login

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.kakao.sdk.user.UserApiClient
import com.won983212.mongle.R
import com.won983212.mongle.databinding.ActivityLoginBinding
import com.won983212.mongle.presentation.base.BaseDataActivity
import com.won983212.mongle.presentation.view.agree.AgreeActivity
import com.won983212.mongle.presentation.view.login.LoginActivity.Companion.EXTRA_REDIRECT_TO
import dagger.hilt.android.AndroidEntryPoint

/**
 * ## Extras
 * * **(선택)** [EXTRA_REDIRECT_TO]: [Intent] -
 * 로그인이 끝나면, 지정한 intent를 startActivity
 */
@AndroidEntryPoint
class LoginActivity : BaseDataActivity<ActivityLoginBinding>() {

    private val viewModel by viewModels<LoginViewModel>()
    override val layoutId: Int = R.layout.activity_login

    override fun onInitialize() {
        binding.viewModel = viewModel

        binding.btnLogin.setOnClickListener {
            loginWithKakao()
        }

        val redirectTo = intent.getParcelableExtra(EXTRA_REDIRECT_TO) as? Intent

        viewModel.attachDefaultHandlers(this)
        viewModel.eventReadyForRegister.observe(this) {
            Intent(this, AgreeActivity::class.java).apply {
                putExtra(AgreeActivity.EXTRA_REDIRECT_TO, redirectTo)
                startActivity(this)
            }
            finish()
        }

        viewModel.eventLoggedIn.observe(this) {
            if (redirectTo != null) {
                startActivity(redirectTo)
            }
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
            UserApiClient.instance.loginWithKakaoAccount(this) { token, error ->
                if (error != null) {
                    Log.e("KakaoLogin", error.toString())
                    Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
                } else if (token != null) {
                    viewModel.doLoginWithKakaoToken(token)
                }
            }
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

    companion object {
        const val EXTRA_REDIRECT_TO = "redirectTo"
    }
}