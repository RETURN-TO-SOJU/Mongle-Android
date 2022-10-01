package com.won983212.mongle.data.source.local

import android.content.Context
import com.won983212.mongle.data.source.PasswordDataSource
import com.won983212.mongle.data.source.SecurePropertiesSource
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

internal class LocalPasswordDataSource
@Inject constructor(
    @ApplicationContext context: Context
) : SecurePropertiesSource(context), PasswordDataSource {

    override val preferenceName = "pwd_pref"

    override fun getScreenPassword(): String? {
        return secureProperties.getString(KEY_PWD_SCREEN, null)
    }

    override fun setScreenPassword(password: String?) {
        setString(KEY_PWD_SCREEN, password)
    }

    override fun getDataKeyPassword(): String? {
        return secureProperties.getString(KEY_PWD_ENCRYPT, null)
    }

    override fun setDataKeyPassword(password: String?) {
        setString(KEY_PWD_ENCRYPT, password)
    }

    private fun setString(key: String, str: String?) {
        val editor = secureProperties.edit()
        if (str != null) {
            editor.putString(key, str)
        } else {
            editor.remove(key)
        }
        editor.apply()
    }

    companion object {
        private const val KEY_PWD_SCREEN = "pwdScreen"
        private const val KEY_PWD_ENCRYPT = "pwdEncrypt"
    }
}