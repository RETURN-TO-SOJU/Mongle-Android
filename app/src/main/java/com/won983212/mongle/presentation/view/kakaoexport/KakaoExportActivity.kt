package com.won983212.mongle.presentation.view.kakaoexport

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.viewModels
import com.won983212.mongle.R
import com.won983212.mongle.presentation.util.attachCompatVectorAnim
import com.won983212.mongle.presentation.util.toastLong
import com.won983212.mongle.databinding.ActivityKakaotalkExportBinding
import com.won983212.mongle.presentation.base.BaseDataActivity
import com.won983212.mongle.presentation.view.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class KakaoExportActivity : BaseDataActivity<ActivityKakaotalkExportBinding>() {
    private val viewModel by viewModels<KakaoExportViewModel>()
    override val layoutId: Int = R.layout.activity_kakaotalk_export

    override fun onInitialize() {
        checkLogin()

        binding.viewModel = viewModel
        binding.imageKakaotalkExportSending.attachCompatVectorAnim(
            R.drawable.avd_bounce_tired,
            true
        )
        binding.imageKakaotalkExportAnalyzing.attachCompatVectorAnim(R.drawable.avd_analyzing, true)

        viewModel.eventErrorMessage.observe(this) {
            toastLong(it)
            finish()
        }
    }

    private fun sendKakaoMessagesData() {
        val uri = intent.getParcelableExtra(Intent.EXTRA_STREAM) as? Uri
        val name = intent.getStringExtra(Intent.EXTRA_SUBJECT)

        // TODO Apply name to sending file name
        // 소마 팀 3 카카오톡 대화
        Log.d("KakaoExportActivity", "NAME: $name")
        if (uri != null && name != null) {
            val stream = contentResolver.openInputStream(uri)
            if (stream != null) {
                viewModel.uploadKakaotalk(stream)
            } else {
                onCantFindFile()
            }
        } else {
            onCantFindFile()
        }
    }

    private fun checkLogin() {
        registerForActivityResult(LoginActivity.LoginResultContract()) {
            when (it) {
                LoginActivity.LoginResult.REGISTER, LoginActivity.LoginResult.LOGIN -> {
                    sendKakaoMessagesData()
                }
                LoginActivity.LoginResult.CANCELLED, null -> {
                    toastLong(R.string.error_need_login_to_analyze)
                    finish()
                }
            }
        }.launch(null)
    }

    private fun onCantFindFile() {
        toastLong(R.string.error_cant_find_shared_file)
        finish()
    }
}