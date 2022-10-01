package com.won983212.mongle.data.source

interface PasswordDataSource {
    fun getScreenPassword(): String?

    fun setScreenPassword(password: String?)

    fun getDataKeyPassword(): String?

    fun setDataKeyPassword(password: String?)
}