package com.won983212.mongle.repository

interface PasswordRepository {
    fun getPassword(): String

    fun setPassword(password: String)
}