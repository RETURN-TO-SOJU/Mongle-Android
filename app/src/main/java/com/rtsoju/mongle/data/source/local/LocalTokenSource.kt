package com.rtsoju.mongle.data.source.local

import android.content.Context
import com.rtsoju.mongle.data.source.SecurePropertiesSource
import com.rtsoju.mongle.domain.model.OAuthLoginToken
import com.rtsoju.mongle.util.parseLocalDateTime
import com.rtsoju.mongle.util.toEpochMilli
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

internal class LocalTokenSource @Inject constructor(
    @ApplicationContext context: Context
) : SecurePropertiesSource(context) {

    override val preferenceName = "tkn_pref"

    private var cachedToken: OAuthLoginToken? = null

    fun getToken(): OAuthLoginToken {
        if (cachedToken == null) {
            cachedToken = OAuthLoginToken(
                secureProperties.getString(KEY_ACCESS_TOKEN, null) ?: "",
                parseLocalDateTime(secureProperties.getLong(KEY_ACCESS_EXPIRES_AT, 0)),
                secureProperties.getString(KEY_REFRESH_TOKEN, null) ?: "",
                parseLocalDateTime(secureProperties.getLong(KEY_REFRESH_EXPIRES_AT, 0))
            )
        }
        return cachedToken as OAuthLoginToken
    }

    fun setToken(token: OAuthLoginToken) {
        cachedToken = token
        secureProperties.edit()
            .putString(KEY_ACCESS_TOKEN, token.accessToken)
            .putLong(KEY_ACCESS_EXPIRES_AT, token.accessTokenExpiresAt.toEpochMilli())
            .putString(KEY_REFRESH_TOKEN, token.refreshToken)
            .putLong(KEY_REFRESH_EXPIRES_AT, token.refreshTokenExpiresAt.toEpochMilli())
            .apply()
    }

    fun clear() {
        setToken(OAuthLoginToken.EMPTY_TOKEN)
    }

    companion object {
        private const val KEY_ACCESS_TOKEN = "access"
        private const val KEY_ACCESS_EXPIRES_AT = "expiresAccess"
        private const val KEY_REFRESH_TOKEN = "refresh"
        private const val KEY_REFRESH_EXPIRES_AT = "expiresRefresh"
    }
}