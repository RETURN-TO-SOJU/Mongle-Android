package com.won983212.mongle.domain.repository

import com.won983212.mongle.data.source.local.config.ConfigKey
import com.won983212.mongle.data.source.local.config.SettingEditor

interface ConfigRepository {

    /**
     * ConfigKey에 해당하는 setting값을 가져온다.
     * 만약 값이 없으면 defaultValue를 가져오며, 이 값을 저장한다.
     */
    fun <T> get(key: ConfigKey<T>): T

    /**
     * setting을 수정할 수 있는 editor를 생성한다. 이 editor는 ConfigKey로 쉽게 다룰 수 있도록
     * SharedPreference를 wrapping하여 만든 클래스이다.
     */
    fun editor(): SettingEditor
}