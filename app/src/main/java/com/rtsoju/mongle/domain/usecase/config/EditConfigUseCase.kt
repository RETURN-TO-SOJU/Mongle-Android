package com.rtsoju.mongle.domain.usecase.config

import com.rtsoju.mongle.data.source.local.config.SettingEditor
import com.rtsoju.mongle.domain.repository.ConfigRepository
import javax.inject.Inject

/**
 * setting을 수정할 수 있는 editor를 생성한다. 이 editor는 ConfigKey로 쉽게 다룰 수 있도록
 * SharedPreference를 wrapping하여 만든 클래스이다.
 */
class EditConfigUseCase @Inject constructor(
    private val configRepository: ConfigRepository
) {
    operator fun invoke(): SettingEditor {
        return configRepository.editor()
    }
}