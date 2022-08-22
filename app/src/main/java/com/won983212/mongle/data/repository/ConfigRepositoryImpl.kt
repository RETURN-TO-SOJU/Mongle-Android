package com.won983212.mongle.data.repository

import android.content.SharedPreferences
import com.won983212.mongle.data.local.source.ConfigDataSource
import com.won983212.mongle.domain.repository.ConfigRepository
import javax.inject.Inject

internal class ConfigRepositoryImpl @Inject constructor(
    private val configDataSource: ConfigDataSource
) : ConfigRepository {

    override fun get(): SharedPreferences = configDataSource.getPreference()
}