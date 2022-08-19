package com.won983212.mongle.data.repository

import android.content.Context
import com.won983212.mongle.common.model.OAuthLoginToken
import com.won983212.mongle.repository.TokenRepository
import javax.inject.Inject

internal class FileTokenRepository
@Inject constructor(
    context: Context
) : SecureFileRepository(context), TokenRepository {

    override val preferenceName = "tkn_pref"

    private var cachedToken: OAuthLoginToken? = null

    override fun getToken(): OAuthLoginToken {
        if (cachedToken == null) {
            cachedToken = OAuthLoginToken(
                secureProperties.getString(KEY_ACCESS_TOKEN, null) ?: "",
                secureProperties.getString(KEY_REFRESH_TOKEN, null) ?: ""
            )
        }
        return cachedToken as OAuthLoginToken
    }

    override fun setToken(token: OAuthLoginToken) {
        cachedToken = token
        secureProperties.edit()
            .putString(KEY_ACCESS_TOKEN, token.accessToken)
            .putString(KEY_REFRESH_TOKEN, token.refreshToken)
            .apply()
    }

    companion object {
        private const val KEY_ACCESS_TOKEN = "access"
        private const val KEY_REFRESH_TOKEN = "refresh"
    }
}