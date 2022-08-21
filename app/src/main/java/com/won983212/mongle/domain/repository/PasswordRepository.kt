package com.won983212.mongle.domain.repository

interface PasswordRepository {
    fun getPassword(): String

    fun setPassword(password: String)
}