package com.rtsoju.mongle.data.repository

import com.rtsoju.mongle.data.source.local.config.ConfigDataSource
import com.rtsoju.mongle.data.source.local.config.ConfigKey
import com.rtsoju.mongle.data.source.local.config.SettingEditor
import com.rtsoju.mongle.domain.repository.ConfigRepository
import javax.inject.Inject

internal class ConfigRepositoryImpl @Inject constructor(
    private val configDataSource: ConfigDataSource
) : ConfigRepository {

    override fun <T> get(key: ConfigKey<T>): T =
        configDataSource.get(key)

    override fun editor(): SettingEditor =
        configDataSource.editor()
}