package com.won983212.mongle.data.source.local

import android.content.Context
import com.won983212.mongle.data.source.SecurePropertiesSource
import com.won983212.mongle.data.model.OAuthLoginToken
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

internal class TokenDataSource @Inject constructor(
    @ApplicationContext context: Context
) : SecurePropertiesSource(context) {

    override val preferenceName = "tkn_pref"

    private var cachedToken: OAuthLoginToken? = null

    fun getToken(): OAuthLoginToken {
        if (cachedToken == null) {
            cachedToken = OAuthLoginToken(
                secureProperties.getString(KEY_ACCESS_TOKEN, null) ?: "",
                secureProperties.getString(KEY_REFRESH_TOKEN, null) ?: ""
            )
        }
        return cachedToken as OAuthLoginToken
    }

    fun setToken(token: OAuthLoginToken) {
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