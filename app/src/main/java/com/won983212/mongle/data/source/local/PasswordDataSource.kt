package com.won983212.mongle.data.source.local

import android.content.Context
import com.won983212.mongle.data.source.SecurePropertiesSource
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

internal class PasswordDataSource
@Inject constructor(
    @ApplicationContext context: Context
) : SecurePropertiesSource(context) {

    override val preferenceName = "pwd_pref"

    fun getPassword(): String? {
        return secureProperties.getString(KEY_PWD, null)
    }

    fun setPassword(password: String?) {
        val editor = secureProperties.edit()
        if (password != null) {
            editor.putString(KEY_PWD, password)
        } else {
            editor.remove(KEY_PWD)
        }
        editor.apply()
    }

    companion object {
        private const val KEY_PWD = "pwd"
    }
}