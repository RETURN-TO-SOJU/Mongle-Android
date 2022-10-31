package com.rtsoju.mongle.data.repository

import com.rtsoju.mongle.data.source.local.LocalTokenSource
import com.rtsoju.mongle.data.source.local.LocalUserDataSource
import com.rtsoju.mongle.data.source.remote.RemoteUserDataSource
import com.rtsoju.mongle.data.source.remote.dto.MessageResult
import com.rtsoju.mongle.domain.model.CachePolicy
import com.rtsoju.mongle.domain.model.User
import com.rtsoju.mongle.domain.repository.UserRepository
import javax.inject.Inject

internal class UserRepositoryImpl
@Inject constructor(
    private val localTokenSource: LocalTokenSource,
    private val localUserDataSource: LocalUserDataSource,
    private val remoteUserDataSource: RemoteUserDataSource
) : UserRepository {

    override suspend fun getUserInfo(
        cachePolicy: CachePolicy
    ): Result<User> {
        return cachePolicy.get(object : CachePolicy.CacheableResource<User> {
            override suspend fun loadFromCache(): Result<User> {
                return localUserDataSource.getSavedUser()
            }

            override suspend fun saveCallResult(value: User) {
                localUserDataSource.saveUser(value)
            }

            override suspend fun fetch(): Result<User> {
                return remoteUserDataSource.getUserInfo()
            }

            override fun getResourceName(): String {
                return "USER"
            }
        })
    }

    override suspend fun setFCMToken(fcmToken: String): Result<MessageResult> {
        return remoteUserDataSource.setFCMToken(fcmToken)
    }

    override suspend fun setUsername(username: String): Result<MessageResult> {
        localUserDataSource.setUsername(username)
        return remoteUserDataSource.setUsername(username)
    }

    override suspend fun leaveAccount(): Result<Unit> {
        val result = remoteUserDataSource.leaveAccount()
        result.onSuccess {
            localTokenSource.clear()
        }
        return result
    }
}