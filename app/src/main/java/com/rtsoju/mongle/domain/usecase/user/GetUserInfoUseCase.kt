package com.rtsoju.mongle.domain.usecase.user

import com.rtsoju.mongle.domain.model.CachePolicy
import com.rtsoju.mongle.domain.model.User
import com.rtsoju.mongle.domain.repository.UserRepository
import javax.inject.Inject

/** 유저 정보 가져오기 */
class GetUserInfoUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(cachePolicy: CachePolicy = CachePolicy.DEFAULT): Result<User> {
        return userRepository.getUserInfo(cachePolicy)
    }
}