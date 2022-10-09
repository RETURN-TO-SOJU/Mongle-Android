package com.won983212.mongle.data.source.local

import android.content.Context
import com.won983212.mongle.data.source.SecurePropertiesSource
import com.won983212.mongle.domain.model.User
import com.won983212.mongle.exception.NoResultException
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

internal class LocalUserDataSource
@Inject constructor(
    @ApplicationContext context: Context
) : SecurePropertiesSource(context) {

    override val preferenceName: String = "user_pref"

    fun getSavedUser(): Result<User> {
        val userName = secureProperties.getString(KEY_USERNAME, null)
        val kakaoName = secureProperties.getString(KEY_KAKAONAME, null)
        if (userName == null || kakaoName == null) {
            return Result.failure(NoResultException())
        }
        return Result.success(User(userName, kakaoName))
    }

    fun saveUser(user: User?) {
        if (user == null) {
            secureProperties.edit().clear().apply()
        } else {
            secureProperties.edit()
                .putString(KEY_USERNAME, user.username)
                .putString(KEY_KAKAONAME, user.kakaoName)
                .apply()
        }
    }

    fun setUsername(username: String) {
        val kakaoName = secureProperties.getString(KEY_KAKAONAME, "")
        saveUser(User(username, kakaoName!!))
    }

    companion object {
        private const val KEY_USERNAME = "username"
        private const val KEY_KAKAONAME = "kakaoname"
    }
}