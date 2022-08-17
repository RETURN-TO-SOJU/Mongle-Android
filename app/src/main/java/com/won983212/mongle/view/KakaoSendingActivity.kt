package com.won983212.mongle.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.won983212.mongle.R
import com.won983212.mongle.databinding.ActivityKakaoSendingBinding
import com.won983212.mongle.util.attachCompatAnimLoop

class KakaoSendingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityKakaoSendingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityKakaoSendingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageSending.attachCompatAnimLoop(R.drawable.avd_bounce_tired)
        binding.imageAnalyzing.attachCompatAnimLoop(R.drawable.avd_analyzing)


        /*intent.extras?.let {
            for (key in it.keySet()) {
                Log.i("Test", key + ": " + it[key])
            }
            val uri = it.get(Intent.EXTRA_STREAM) as Uri
            Log.i("Test", uri.toString())

            val instream = contentResolver.openInputStream(uri)
            val instreamR = InputStreamReader(instream)
            Log.i("Test", instreamR.readLines().size.toString())
        }*/
    }
}