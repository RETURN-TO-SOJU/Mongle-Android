package com.won983212.mongle.domain.repository

import com.won983212.mongle.data.model.User
import com.won983212.mongle.data.source.api.RequestLifecycleCallback
import com.won983212.mongle.data.source.remote.model.MessageResult

interface UserRepository {

    /**
     * 유저 정보 가져오기
     */
    suspend fun getUserInfo(
        callback: RequestLifecycleCallback
    ): User?

    /**
     * 알림 받을 FCM토큰 전달
     */
    suspend fun setFCMToken(
        callback: RequestLifecycleCallback,
        fcmToken: String
    ): MessageResult?

    /**
     * 이름 설정
     */
    suspend fun setUsername(
        callback: RequestLifecycleCallback,
        username: String
    ): MessageResult?

    /**
     * 탈퇴하기
     */
    suspend fun leaveAccount(
        callback: RequestLifecycleCallback
    ): MessageResult?
}