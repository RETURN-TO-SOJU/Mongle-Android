package com.won983212.mongle.view.kakaoexport

import android.content.Intent
import android.net.Uri
import androidx.activity.viewModels
import com.won983212.mongle.R
import com.won983212.mongle.common.base.BaseDataActivity
import com.won983212.mongle.common.util.attachCompatAnimLoop
import com.won983212.mongle.common.util.toastLong
import com.won983212.mongle.databinding.ActivityKakaotalkExportBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class KakaoExportActivity : BaseDataActivity<ActivityKakaotalkExportBinding>() {
    private val viewModel by viewModels<KakaoExportViewModel>()

    override val layoutId: Int = R.layout.activity_kakaotalk_export

    override fun onInitialize() {
        binding.viewModel = viewModel
        binding.imageKakaotalkExportSending.attachCompatAnimLoop(R.drawable.avd_bounce_tired)
        binding.imageKakaotalkExportAnalyzing.attachCompatAnimLoop(R.drawable.avd_analyzing)

        viewModel.eventErrorMessage.observe(this) {
            toastLong(it)
            finish()
        }

        val extras = intent.extras
        if (extras != null) {
            val uri = extras.get(Intent.EXTRA_STREAM) as Uri
            val stream = contentResolver.openInputStream(uri)
            if (stream != null) {
                viewModel.sendKakaoTalkData(stream)
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