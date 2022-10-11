package com.rtsoju.mongle.data.source

interface PasswordDataSource {
    fun getScreenPassword(): String?

    fun setScreenPassword(password: String?)

    fun getDataKeyPassword(): String?

    fun setDataKeyPassword(password: String?)
}