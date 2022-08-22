package com.won983212.mongle.presentation.view.kakaoexport

import android.content.Intent
import android.net.Uri
import androidx.activity.viewModels
import com.won983212.mongle.R
import com.won983212.mongle.common.util.attachCompatVectorAnim
import com.won983212.mongle.common.util.toastLong
import com.won983212.mongle.databinding.ActivityKakaotalkExportBinding
import com.won983212.mongle.presentation.base.BaseDataActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class KakaoExportActivity : BaseDataActivity<ActivityKakaotalkExportBinding>() {
    private val viewModel by viewModels<KakaoExportViewModel>()

    override val layoutId: Int = R.layout.activity_kakaotalk_export

    override fun onInitialize() {
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

        val extras = intent.extras
        if (extras != null) {
            val uri = extras.get(Intent.EXTRA_STREAM) as Uri
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

    private fun onCantFindFile() {
        toastLong(R.string.error_cant_find_shared_file)
        finish()
    }
}