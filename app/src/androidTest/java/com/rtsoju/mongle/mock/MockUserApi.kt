package com.rtsoju.mongle.mock

import com.rtsoju.mongle.data.source.api.UserApi
import com.rtsoju.mongle.data.source.remote.dto.MessageResult
import com.rtsoju.mongle.data.source.remote.dto.request.FCMTokenRequest
import com.rtsoju.mongle.data.source.remote.dto.request.UsernameRequest
import com.rtsoju.mongle.data.source.remote.dto.response.UserResponse
import com.rtsoju.mongle.domain.model.User
import com.rtsoju.mongle.util.generateUser1

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