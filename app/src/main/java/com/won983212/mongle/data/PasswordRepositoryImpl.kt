package com.won983212.mongle.data

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.won983212.mongle.repository.PasswordRepository
import javax.inject.Inject

internal class PasswordRepositoryImpl
@Inject constructor(
    val context: Context
) : PasswordRepository {

    private val pwdPreferences: SharedPreferences by lazy {
        val masterKey = MasterKey
            .Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        EncryptedSharedPreferences.create(
            context,
            PWD_PREF_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    override fun getPassword(): String {
        return pwdPreferences.getString(KEY_PWD, "") ?: ""
    }

    override fun setPassword(password: String) {
        pwdPreferences.edit()
            .putString(KEY_PWD, password)
            .apply()
    }

    companion object {
        private const val PWD_PREF_NAME = "pwd_pref"
        private const val KEY_PWD = "pwd"
    }
}