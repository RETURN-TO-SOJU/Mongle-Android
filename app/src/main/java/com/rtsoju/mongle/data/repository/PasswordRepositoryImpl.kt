package com.rtsoju.mongle.data.repository

import android.util.Base64
import com.rtsoju.mongle.data.source.PasswordDataSource
import com.rtsoju.mongle.domain.repository.PasswordRepository
import com.rtsoju.mongle.exception.CannotDecryptException
import com.rtsoju.mongle.util.generateFillZero
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import javax.inject.Inject

internal class PasswordRepositoryImpl
@Inject constructor(
    private val passwordDataSource: PasswordDataSource
) : PasswordRepository {

    override fun checkScreenPassword(password: String): Boolean {
        return passwordDataSource.getScreenPassword() == password
    }

    override fun hasScreenPassword(): Boolean {
        return passwordDataSource.getScreenPassword() != null
    }

    override fun setScreenPassword(password: String?) {
        return passwordDataSource.setScreenPassword(password)
    }

    override fun decryptByKeyPassword(data: String): String {
        try {
            val pwd = passwordDataSource.getDataKeyPassword()?.toByteArray(PASSWORD_CHARSET)
                ?: return data
            val key = SecretKeySpec(pwd, "AES")
            val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
            val decodedData = Base64.decode(data, Base64.DEFAULT)
            cipher.init(
                Cipher.DECRYPT_MODE,
                key,
                IvParameterSpec(decodedData.sliceArray(0 until 16))
            )
            return String(cipher.doFinal(decodedData.sliceArray(16 until decodedData.size)))
        } catch (e: Exception) {
            throw CannotDecryptException(e)
        }
    }

    override fun hasDataKeyPassword(): Boolean {
        return passwordDataSource.getDataKeyPassword() != null
    }

    override fun setDataKeyPassword(password: String?) {
        var filteredPwd = password
        if (filteredPwd != null) {
            if (filteredPwd.length > PASSWORD_LEN) {
                filteredPwd = filteredPwd.substring(0, PASSWORD_LEN)
            } else if (filteredPwd.length < PASSWORD_LEN) {
                filteredPwd += generateFillZero(PASSWORD_LEN - filteredPwd.length)
            }
        }
        passwordDataSource.setDataKeyPassword(filteredPwd)
    }

    override fun getDataPassword(): String? {
        return passwordDataSource.getDataKeyPassword()
    }

    companion object {
        @JvmStatic
        val PASSWORD_CHARSET = Charsets.UTF_8

        @JvmStatic
        val PASSWORD_LEN = 32
    }
}