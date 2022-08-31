package com.won983212.mongle.debug.mock

import com.won983212.mongle.data.model.User
import com.won983212.mongle.data.source.api.UserApi
import com.won983212.mongle.data.source.remote.model.MessageResult
import com.won983212.mongle.data.source.remote.model.request.FCMTokenRequest
import com.won983212.mongle.data.source.remote.model.request.UsernameRequest
import com.won983212.mongle.debug.mock.MockAuthApi.Companion.checkToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class MockUserApi : UserApi {

    override suspend fun getUserInfo(token: String): User =
        withContext(Dispatchers.IO) {
            delay(300)
            checkToken(token)
            User("소마", "soma")
        }

    override suspend fun setFCMToken(
        token: String,
        fcmToken: FCMTokenRequest
    ): MessageResult =
        withContext(Dispatchers.IO) {
            delay(500)
            checkToken(token)
            MessageResult("complete")
        }

    override suspend fun setUsername(
        token: String,
        username: UsernameRequest
    ): MessageResult =
        withContext(Dispatchers.IO) {
            delay(500)
            checkToken(token)
            MessageResult("complete")
        }

    override suspend fun leaveAccount(token: String): MessageResult =
        withContext(Dispatchers.IO) {
            delay(2000)
            checkToken(token)
            MessageResult("complete")
        }
}