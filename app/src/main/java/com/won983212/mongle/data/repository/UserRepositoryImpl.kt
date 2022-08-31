package com.won983212.mongle.data.repository

import com.won983212.mongle.data.model.User
import com.won983212.mongle.data.source.local.LocalTokenSource
import com.won983212.mongle.data.source.remote.RemoteUserDataSource
import com.won983212.mongle.data.source.remote.model.MessageResult
import com.won983212.mongle.domain.repository.UserRepository
import javax.inject.Inject

internal class UserRepositoryImpl
@Inject constructor(
    private val localTokenSource: LocalTokenSource,
    private val userDataSource: RemoteUserDataSource
) : UserRepository {

    override suspend fun getUserInfo(): Result<User> =
        userDataSource.getUserInfo()

    override suspend fun setFCMToken(fcmToken: String): Result<MessageResult> =
        userDataSource.setFCMToken(fcmToken)

    override suspend fun setUsername(username: String): Result<MessageResult> =
        userDataSource.setUsername(username)

    override suspend fun leaveAccount(): Result<MessageResult> {
        val result = userDataSource.leaveAccount()
        result.onSuccess {
            localTokenSource.clear()
        }
        return result
    }
}