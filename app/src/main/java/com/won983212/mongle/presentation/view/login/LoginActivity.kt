package com.won983212.mongle.presentation.view.login

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.viewModels
import com.kakao.sdk.user.UserApiClient
import com.won983212.mongle.R
import com.won983212.mongle.common.util.toastLong
import com.won983212.mongle.common.util.toastShort
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
            setLoginResult(LoginResult.REGISTER)
            finish()
        }

        viewModel.eventLoggedIn.observe(this) {
            if (redirectTo != null) {
                startActivity(redirectTo)
            }
            setLoginResult(LoginResult.LOGIN)
            finish()
        }

        viewModel.checkCanAutoLogin()
    }

    private fun setLoginResult(result: LoginResult) {
        setResult(RESULT_OK, Intent().putExtra(RESULT_LOGIN, result))
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
                    Log.d(
                        "KakaoLogin",
                        "ACCESS: ${token.accessToken} / REFRESH: ${token.refreshToken}"
                    )
                    viewModel.doLoginWithKakaoToken(token)
                }
            }
        }
    }

    enum class LoginResult {
        /** 사용자가 회원가입을 한 경우 */
        REGISTER,

        /** 사용자가 로그인을 한 경우 (기존 회원이라면 회원가입 절차없이 로그인) */
        LOGIN,

        /** 사용자가 로그인을 거부한 경우 (뒤로가기, 종료 등) */
        CANCELLED
    }

    /**
     * Input으로 [EXTRA_REDIRECT_TO]를 지정한다.
     */
    class LoginResultContract : ActivityResultContract<Intent?, LoginResult>() {
        override fun createIntent(context: Context, input: Intent?): Intent =
            Intent(context, LoginActivity::class.java).apply {
                putExtra(EXTRA_REDIRECT_TO, input)
            }

        override fun parseResult(resultCode: Int, intent: Intent?): LoginResult {
            if (resultCode == RESULT_OK && intent != null) {
                return intent.getSerializableExtra(RESULT_LOGIN) as LoginResult
            }
            return LoginResult.CANCELLED
        }
    }

    companion object {
        const val EXTRA_REDIRECT_TO = "redirectTo"
        private const val RESULT_LOGIN = "loginResult"
    }
}