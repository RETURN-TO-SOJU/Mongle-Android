package com.rtsoju.mongle.domain.repository

import com.rtsoju.mongle.data.source.local.config.ConfigKey
import com.rtsoju.mongle.data.source.local.config.SettingEditor

interface ConfigRepository {

    fun <T> get(key: ConfigKey<T>): T

    fun editor(): SettingEditor
}