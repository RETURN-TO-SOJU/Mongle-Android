package com.won983212.mongle.data.repository

import com.won983212.mongle.data.model.OAuthLoginToken
import com.won983212.mongle.data.model.User
import com.won983212.mongle.data.source.api.RequestLifecycleCallback
import com.won983212.mongle.data.source.local.LocalTokenSource
import com.won983212.mongle.data.source.remote.RemoteUserDataSource
import com.won983212.mongle.data.source.remote.model.MessageResult
import com.won983212.mongle.domain.repository.AuthRepository
import com.won983212.mongle.domain.repository.UserRepository
import javax.inject.Inject

internal class UserRepositoryImpl
@Inject constructor(
    private val localTokenSource: LocalTokenSource,
    private val userDataSource: RemoteUserDataSource
) : UserRepository {

    override suspend fun getUserInfo(
        callback: RequestLifecycleCallback
    ): User? = userDataSource.getUserInfo(callback)

    override suspend fun setFCMToken(
        callback: RequestLifecycleCallback,
        fcmToken: String
    ): MessageResult? =
        userDataSource.setFCMToken(callback, fcmToken)

    override suspend fun setUsername(
        callback: RequestLifecycleCallback,
        username: String
    ): MessageResult? =
        userDataSource.setUsername(callback, username)

    override suspend fun leaveAccount(callback: RequestLifecycleCallback): MessageResult? {
        val result = userDataSource.leaveAccount(callback)
        if (result != null) {
            localTokenSource.clear()
        }
        return result
    }
}