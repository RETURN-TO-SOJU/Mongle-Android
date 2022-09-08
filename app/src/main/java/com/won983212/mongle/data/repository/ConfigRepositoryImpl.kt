package com.won983212.mongle.data.repository

import com.won983212.mongle.data.source.local.config.ConfigDataSource
import com.won983212.mongle.data.source.local.config.ConfigKey
import com.won983212.mongle.data.source.local.config.SettingEditor
import com.won983212.mongle.domain.repository.ConfigRepository
import javax.inject.Inject

internal class ConfigRepositoryImpl @Inject constructor(
    private val configDataSource: ConfigDataSource
) : ConfigRepository {

    override fun <T> get(key: ConfigKey<T>): T =
        configDataSource.get(key)

    override fun editor(): SettingEditor =
        configDataSource.editor()
}