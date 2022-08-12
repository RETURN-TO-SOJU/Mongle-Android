package com.won983212.mongle.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.won983212.mongle.R
import java.io.InputStreamReader

class KakaoReceiveActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kakao_receive)

        intent.extras?.let {
            for (key in it.keySet()) {
                Log.i("Test", key + ": " + it[key])
            }
            val uri = it.get(Intent.EXTRA_STREAM) as Uri
            Log.i("Test", uri.toString())

            val instream = contentResolver.openInputStream(uri)
            val instreamR = InputStreamReader(instream)
            Log.i("Test", instreamR.readLines().size.toString())
        }
        /*intent.data?.let {
            val instream = contentResolver.openInputStream(intent.data!!)
            val instreamR = InputStreamReader(instream)
            Log.i("Test", instreamR.readText())
        }*/
    }
}