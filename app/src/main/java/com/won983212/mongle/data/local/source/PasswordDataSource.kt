package com.won983212.mongle.data.local.source

import android.content.Context
import com.won983212.mongle.data.local.SecurePropertiesSource
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

internal class PasswordDataSource
@Inject constructor(
    @ApplicationContext context: Context
) : SecurePropertiesSource(context) {

    override val preferenceName = "pwd_pref"

    fun getPassword(): String {
        return secureProperties.getString(KEY_PWD, "") ?: ""
    }

    fun setPassword(password: String) {
        secureProperties.edit()
            .putString(KEY_PWD, password)
            .apply()
    }

    companion object {
        private const val KEY_PWD = "pwd"
    }
}