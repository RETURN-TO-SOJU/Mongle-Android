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
import com.rtsoju.mongle.presentation.view.login.LoginFlow
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

        binding.btnKakaotalkExportAnalyzingExit.setOnClickListener {
            finish()
        }

        viewModel.eventErrorMessage.observe(this) {
            toastLong(it)
            finish()
        }
    }

    private fun checkLogin() {
        LoginFlow(this) {
            when (it) {
                LoginFlow.LoginResult.REGISTER, LoginFlow.LoginResult.LOGIN -> {
                    sendKakaoMessagesData()
                }
                LoginFlow.LoginResult.CANCELLED -> {
                    toastLong(R.string.error_need_login_to_analyze)
                    finish()
                }
            }
        }.launch()
    }

    private fun parseRoomName(subject: String): String {
        val matchesGroup = Regex("(.+) \\d+ 카카오톡 대화\$").matchEntire(subject)
        matchesGroup?.let {
            return it.groupValues[1]
        }

        val matchesIndividual = Regex("(.+) 님과 카카오톡 대화\$").matchEntire(subject)
        matchesIndividual?.let {
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
                InputRoomNameDialog(this, roomName)
                    .setOnSubmitName { viewModel.uploadKakaotalkWithRoom(it, stream) }
                    .setOnCancelledListener {
                        toastLong(R.string.error_cancelled_analyze)
                        finish()
                    }
                    .open()
            } else {
                onCantFindFile()
            }
        } else {
            onCantFindFile()
        }
    }

    private fun onCantFindFile() {
        toastLong(R.string.error_cant_find_shared_file)
        finish()
    }
}