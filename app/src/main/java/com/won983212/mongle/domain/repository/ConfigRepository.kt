package com.won983212.mongle.domain.repository

import com.won983212.mongle.data.source.local.config.ConfigKey
import com.won983212.mongle.data.source.local.config.SettingEditor

interface ConfigRepository {
    fun <T> get(key: ConfigKey<T>): T

    fun editor(): SettingEditor
}