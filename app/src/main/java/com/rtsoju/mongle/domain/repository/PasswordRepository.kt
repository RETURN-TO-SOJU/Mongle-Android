package com.rtsoju.mongle.domain.repository

interface PasswordRepository {

    fun checkScreenPassword(password: String): Boolean

    fun hasScreenPassword(): Boolean

    fun setScreenPassword(password: String?)

    fun decryptByKeyPassword(data: String): String

    fun hasDataKeyPassword(): Boolean

    fun setDataKeyPassword(password: String?)

    fun getDataPassword(): String?
}