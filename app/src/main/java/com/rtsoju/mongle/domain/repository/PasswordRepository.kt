package com.rtsoju.mongle.domain.repository

import java.io.InputStream

interface PasswordRepository {

    fun checkScreenPassword(password: String): Boolean

    fun hasScreenPassword(): Boolean

    fun setScreenPassword(password: String?)

    fun decryptByKeyPassword(data: String): String

    fun hasDataKeyPassword(): Boolean

    fun makePwdKakaotalkDataPacket(dataStream: InputStream): ByteArray

    fun setDataKeyPassword(password: String?)
}