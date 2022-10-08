package com.won983212.mongle.debug.mock

import android.util.Log
import com.won983212.mongle.data.mapper.toDomainModel
import com.won983212.mongle.data.source.api.AuthApi
import com.won983212.mongle.data.source.remote.dto.response.LoginResponse
import com.won983212.mongle.domain.model.OAuthLoginToken
import com.won983212.mongle.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.time.LocalDateTime

class MockAuthApi : AuthApi {

    private fun generateToken(): LoginResponse {
        val now = LocalDateTime.now()
        val genToken = "TOKENTOKENTOKENTOKEN"
        val accessExpires = now.plusSeconds(ACCESS_TOKEN_EXPIRES_SEC)
        val refreshExpires = now.plusSeconds(REFRESH_TOKEN_EXPIRES_SEC)
        return LoginResponse("소마", genToken, accessExpires, genToken, refreshExpires, false)
    }

    override suspend fun login(kakaoToken: OAuthLoginToken): LoginResponse =
        withContext(Dispatchers.IO) {
            delay(300)
            generateToken()
        }

    override suspend fun refreshToken(token: OAuthLoginToken): OAuthLoginToken =
        withContext(Dispatchers.IO) {
            delay(500)
            if (token.isRefreshTokenExpired()) {
                throw MockingHttpException("Refresh 토큰이 만료되었습니다. 다시 로그인하세요.")
            }
            Log.d("MockAuthApi", "RefreshToken 발급")
            OAuthLoginToken.fromLoginResult(generateToken().toDomainModel())
        }

    companion object {
        private const val ACCESS_TOKEN_EXPIRES_SEC = 600L
        private const val REFRESH_TOKEN_EXPIRES_SEC = 1200L

        suspend fun checkToken(tokenRepository: AuthRepository) {
            tokenRepository.getAccessToken().onFailure {
                throw it
            }
        }
    }
}