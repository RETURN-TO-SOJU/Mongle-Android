package com.won983212.mongle.domain.usecase.auth

import com.won983212.mongle.domain.repository.AuthRepository
import com.won983212.mongle.domain.usecase.user.GetUserInfoUseCase
import com.won983212.mongle.exception.NeedsLoginException
import retrofit2.HttpException
import javax.inject.Inject

/**
 * Token이 실제로 유효한지 서버에 요청을 보내본다.
 * 만약 유효한 토큰이라면 true, 아니면 false를 리턴한다.
 */
class ValidateTokenUseCase @Inject constructor(
    private val getUserInfo: GetUserInfoUseCase,
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Boolean {
        return try {
            val user = getUserInfo()
            val userObj = user.getOrNull()

            // username이 설정되어있지 않다면 invalid token
            if (userObj != null) {
                return userObj.username.isNotEmpty()
            }

            // 401, 403번 애러인 경우 -> 토큰이 만료된 경우이므로 refresh해보고 성공하면 OK
            // 그외 -> invalid token
            var result = false
            user.onFailure {
                if (it is HttpException && it.code() in arrayOf(401, 403)) {
                    result = authRepository.refreshToken().isSuccess
                }
            }

            return result
        } catch (e: NeedsLoginException) {
            false
        }
    }
}