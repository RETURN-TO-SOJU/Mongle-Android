package com.won983212.mongle.domain.usecase.user

import com.won983212.mongle.data.source.remote.model.MessageResult
import com.won983212.mongle.domain.repository.UserRepository
import javax.inject.Inject

/** 알림 받을 FCM토큰 전달 */
class SetFCMTokenUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(fcmToken: String): Result<MessageResult> {
        return userRepository.setFCMToken(fcmToken)
    }
}