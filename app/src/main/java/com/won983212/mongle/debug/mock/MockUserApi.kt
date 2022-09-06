package com.won983212.mongle.debug.mock

import com.won983212.mongle.data.model.User
import com.won983212.mongle.data.source.api.UserApi
import com.won983212.mongle.data.source.remote.model.MessageResult
import com.won983212.mongle.data.source.remote.model.request.FCMTokenRequest
import com.won983212.mongle.data.source.remote.model.request.UsernameRequest
import com.won983212.mongle.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class MockUserApi(
    private val authRepository: AuthRepository
) : UserApi {

    override suspend fun getUserInfo(): User =
        withContext(Dispatchers.IO) {
            delay(300)
            MockAuthApi.checkToken(authRepository)
            User("소마", "soma")
        }

    override suspend fun setFCMToken(
        fcmToken: FCMTokenRequest
    ): MessageResult =
        withContext(Dispatchers.IO) {
            delay(500)
            MockAuthApi.checkToken(authRepository)
            MessageResult("complete")
        }

    override suspend fun setUsername(
        username: UsernameRequest
    ): MessageResult =
        withContext(Dispatchers.IO) {
            delay(500)
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