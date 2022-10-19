package com.rtsoju.mongle.exception

sealed class AuthException(message: String) : RuntimeException(message)

class NeedsLoginException : AuthException("로그인이 필요합니다.")