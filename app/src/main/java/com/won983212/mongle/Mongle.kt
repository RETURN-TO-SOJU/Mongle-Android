package com.won983212.mongle

import android.app.Application
import android.util.Log
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility

class Mongle : Application() {
    override fun onCreate() {
        super.onCreate()

        // Get key hash
        // Log.i("KeyHash", Utility.getKeyHash(this))

        // Initialize kakao sdk
        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_APP_KEY)
    }
}