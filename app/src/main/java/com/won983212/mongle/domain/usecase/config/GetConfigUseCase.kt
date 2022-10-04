package com.won983212.mongle.domain.usecase.config

import com.won983212.mongle.data.source.local.config.ConfigKey
import com.won983212.mongle.domain.repository.ConfigRepository
import javax.inject.Inject

/**
 * ConfigKey에 해당하는 setting값을 가져온다.
 * 만약 값이 없으면 defaultValue를 가져오며, 이 값을 저장한다.
 */
class GetConfigUseCase @Inject constructor(
    private val configRepository: ConfigRepository
) {
    operator fun <T> invoke(key: ConfigKey<T>): T {
        return configRepository.get(key)
    }
}