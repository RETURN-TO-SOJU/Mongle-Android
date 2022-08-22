package com.won983212.mongle.domain.repository

import android.content.SharedPreferences

interface ConfigRepository {
    fun get(): SharedPreferences
}