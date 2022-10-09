package com.won983212.mongle.mock

import com.won983212.mongle.data.source.api.UserApi
import com.won983212.mongle.data.source.remote.dto.MessageResult
import com.won983212.mongle.data.source.remote.dto.request.FCMTokenRequest
import com.won983212.mongle.data.source.remote.dto.request.UsernameRequest
import com.won983212.mongle.data.source.remote.dto.response.UserResponse
import com.won983212.mongle.domain.model.User
import com.won983212.mongle.util.generateUser1

// TODO Common Test에 생성
class MockUserApi : UserApi {

    private var username: String? = null
    var userResponseGenerator: () -> User = ::generateUser1

    override suspend fun getUserInfo(): UserResponse {
        return userResponseGenerator().let {
            UserResponse(username ?: it.username, it.kakaoName)
        }
    }

    override suspend fun setFCMToken(fcmToken: FCMTokenRequest): MessageResult {
        return MessageResult("ok")
    }

    override suspend fun setUsername(username: UsernameRequest): MessageResult {
        this.username = username.username
        return MessageResult("ok")
    }

    override suspend fun leaveAccount(): MessageResult {
        return MessageResult("ok")
    }
}