package com.won983212.mongle.domain.usecase.user

import com.won983212.mongle.data.model.User
import com.won983212.mongle.domain.repository.UserRepository
import javax.inject.Inject

/** 유저 정보 가져오기 */
class GetUserInfoUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): Result<User> {
        return userRepository.getUserInfo()
    }
}