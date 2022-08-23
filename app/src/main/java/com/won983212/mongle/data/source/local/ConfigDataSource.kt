package com.won983212.mongle.data.source.local

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

internal class ConfigDataSource @Inject constructor(
    @ApplicationContext context: Context
) {
    private val preferences: SharedPreferences

    init {
        preferences = context.getSharedPreferences(CONFIG_PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    fun getPreference(): SharedPreferences {
        return preferences
    }

    companion object {
        private const val CONFIG_PREFERENCE_NAME = "cfg_pref"
    }
}