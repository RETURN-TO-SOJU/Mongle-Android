package com.won983212.mongle

import android.app.Application
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import dagger.hilt.android.HiltAndroidApp

// TODO 폴더 모듈화하기
@HiltAndroidApp
class Mongle : Application() {
    override fun onCreate() {
        super.onCreate()

        // Initialize kakao sdk
        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_APP_KEY)

        // Get key hash
        //logKeyHash()

        // Get FCM Token
        logFCMToken()

        logApiUrl()
    }

    private fun logApiUrl() {
        Log.d("API", BuildConfig.BASE_URL)
    }

    private fun logKeyHash() {
        Log.d("KeyHash", Utility.getKeyHash(this))
    }

    private fun logFCMToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("FCM", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            Log.d("FCM", task.result)
        })
    }
}