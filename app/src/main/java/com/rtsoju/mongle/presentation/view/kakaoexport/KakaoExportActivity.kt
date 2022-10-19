package com.rtsoju.mongle.presentation.view.kakaoexport

import android.content.Intent
import android.net.Uri
import androidx.activity.viewModels
import com.rtsoju.mongle.R
import com.rtsoju.mongle.databinding.ActivityKakaotalkExportBinding
import com.rtsoju.mongle.presentation.base.BaseDataActivity
import com.rtsoju.mongle.presentation.util.attachCompatVectorAnim
import com.rtsoju.mongle.presentation.util.getParcelableExtraCompat
import com.rtsoju.mongle.presentation.util.toastLong
import com.rtsoju.mongle.presentation.view.dialog.InputRoomNameDialog
import com.rtsoju.mongle.presentation.view.login.LoginActivity
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

    private fun parseRoomName(subject: String): String {
        val matches = Regex("(.+) \\d+ 카카오톡 대화\$").matchEntire(subject)
        matches?.let {
            return it.groupValues[1]
        }
        return ""
    }

    private fun sendKakaoMessagesData() {
        val uri: Uri? = intent.getParcelableExtraCompat(Intent.EXTRA_STREAM)
        val name = intent.getStringExtra(Intent.EXTRA_SUBJECT)

        if (uri != null) {
            val stream = contentResolver.openInputStream(uri)
            if (stream != null) {
                val roomName = parseRoomName(name ?: "")
                InputRoomNameDialog(this, roomName) {
                    viewModel.uploadKakaotalk(it, stream)
                }.open()
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