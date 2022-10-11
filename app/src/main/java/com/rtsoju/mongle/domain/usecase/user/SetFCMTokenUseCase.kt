package com.rtsoju.mongle.domain.usecase.user

import com.rtsoju.mongle.data.source.remote.dto.MessageResult
import com.rtsoju.mongle.domain.repository.UserRepository
import javax.inject.Inject

/** 알림 받을 FCM토큰 전달 */
class SetFCMTokenUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(fcmToken: String): Result<MessageResult> {
        return userRepository.setFCMToken(fcmToken)
    }
}