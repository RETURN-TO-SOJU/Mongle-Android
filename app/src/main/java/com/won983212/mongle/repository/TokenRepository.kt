package com.won983212.mongle.repository

import com.won983212.mongle.common.model.OAuthLoginToken

interface TokenRepository {
    fun getToken(): OAuthLoginToken

    fun setToken(token: OAuthLoginToken)
}