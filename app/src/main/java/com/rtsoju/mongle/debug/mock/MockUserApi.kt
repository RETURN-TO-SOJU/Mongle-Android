package com.rtsoju.mongle.debug.mock

import com.rtsoju.mongle.data.source.api.UserApi
import com.rtsoju.mongle.data.source.remote.dto.MessageResult
import com.rtsoju.mongle.data.source.remote.dto.request.FCMTokenRequest
import com.rtsoju.mongle.data.source.remote.dto.request.UsernameRequest
import com.rtsoju.mongle.data.source.remote.dto.response.UserResponse
import com.rtsoju.mongle.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class MockUserApi(
    private val authRepository: AuthRepository
) : UserApi {

    override suspend fun getUserInfo(): UserResponse =
        withContext(Dispatchers.IO) {
            MockAuthApi.checkToken(authRepository)
            UserResponse("소마", "soma")
        }

    override suspend fun setFCMToken(
        fcmToken: FCMTokenRequest
    ): MessageResult =
        withContext(Dispatchers.IO) {
            MockAuthApi.checkToken(authRepository)
            MessageResult("complete")
        }

    override suspend fun setUsername(
        username: UsernameRequest
    ): MessageResult =
        withContext(Dispatchers.IO) {
            MockAuthApi.checkToken(authRepository)
            MessageResult("complete")
        }

    override suspend fun leaveAccount(): MessageResult =
        withContext(Dispatchers.IO) {
            delay(2000)
            MockAuthApi.checkToken(authRepository)
            MessageResult("complete")
        }
}