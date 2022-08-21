package com.won983212.mongle.presentation.view.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.kakao.sdk.user.UserApiClient
import com.won983212.mongle.databinding.ActivityLoginBinding
import com.won983212.mongle.presentation.view.agree.AgreeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            loginWithKakao()
        }

        viewModel.attachDefaultLoadingHandler(this)
        viewModel.attachDefaultErrorHandler(this)
        viewModel.eventLoggedIn.observe(this) {
            Intent(this, AgreeActivity::class.java).apply {
                startActivity(this)
            }
            finish()
        }
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
}