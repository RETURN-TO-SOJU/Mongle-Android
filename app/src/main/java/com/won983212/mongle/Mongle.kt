package com.won983212.mongle

import android.app.Application
import android.util.Log
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import dagger.hilt.android.HiltAndroidApp

// TODO 폴더 모듈화하기
@HiltAndroidApp
class Mongle : Application() {
    override fun onCreate() {
        super.onCreate()

        // Get key hash
        Log.i("KeyHash", Utility.getKeyHash(this))

        // Initialize kakao sdk
        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_APP_KEY)

        // Get FCM Token
        /*FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("FCM", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            Log.d("FCM", task.result)
        })*/
    }
}