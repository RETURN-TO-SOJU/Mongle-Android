package com.won983212.mongle.data.source

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

internal abstract class SecurePropertiesSource(
    context: Context
) {
    abstract val preferenceName: String

    protected val secureProperties: SharedPreferences by lazy {
        val masterKey = MasterKey
            .Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        try {
            getEncryptedPreference(context, masterKey)
        } catch (e: Exception) {
            context.getSharedPreferences(preferenceName, 0).edit().clear().commit()
            getEncryptedPreference(context, masterKey)
        }
    }

    private fun getEncryptedPreference(context: Context, masterKey: MasterKey): SharedPreferences {
        return EncryptedSharedPreferences.create(
            context,
            preferenceName,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }
}