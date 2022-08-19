package com.won983212.mongle.data.repository

import android.content.Context
import com.won983212.mongle.repository.PasswordRepository
import javax.inject.Inject

internal class FilePasswordRepository
@Inject constructor(
    context: Context
) : SecureFileRepository(context), PasswordRepository {

    override val preferenceName = "pwd_pref"

    override fun getPassword(): String {
        return secureProperties.getString(KEY_PWD, "") ?: ""
    }

    override fun setPassword(password: String) {
        secureProperties.edit()
            .putString(KEY_PWD, password)
            .apply()
    }

    companion object {
        private const val KEY_PWD = "pwd"
    }
}