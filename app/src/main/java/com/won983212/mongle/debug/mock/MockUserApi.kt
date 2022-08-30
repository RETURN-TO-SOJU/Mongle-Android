package com.won983212.mongle.debug.mock

import com.won983212.mongle.data.model.OAuthLoginToken
import com.won983212.mongle.data.model.User
import com.won983212.mongle.data.source.api.UserApi
import com.won983212.mongle.data.source.remote.model.MessageResult
import com.won983212.mongle.data.source.remote.model.request.FCMTokenRequest
import com.won983212.mongle.data.source.remote.model.request.UsernameRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MockUserApi : UserApi {

    override suspend fun login(kakaoToken: OAuthLoginToken): OAuthLoginToken =
        withContext(Dispatchers.IO) {
            delay(300)
            val genToken = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)
            OAuthLoginToken(genToken, genToken)
        }

    override suspend fun getUserInfo(token: String): User =
        withContext(Dispatchers.IO) {
            delay(300)
            checkToken(token)
            User("소마", "soma")
        }

    override suspend fun refreshToken(token: OAuthLoginToken): OAuthLoginToken =
        withContext(Dispatchers.IO) {
            delay(500)
            val genToken = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)
            OAuthLoginToken(genToken, genToken)
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

    companion object {
        fun checkToken(token: String) {
            val tokenDate = LocalDateTime.parse(token, DateTimeFormatter.ISO_DATE_TIME)
            if (tokenDate.plusMinutes(10) < LocalDateTime.now()) {
                throw MockingHttpException("토큰이 만료되었습니다.")
            }
        }
    }
}