package com.rtsoju.mongle.domain.model

import java.time.LocalDateTime

data class LoginResult(
    val name: String?,
    val accessToken: String,
    val accessTokenExpiresAt: LocalDateTime,
    val refreshToken: String,
    val refreshTokenExpiresAt: LocalDateTime,
    val isNew: Boolean
)