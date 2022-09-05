package com.won983212.mongle.data.source.local.config

import android.content.SharedPreferences

class SettingEditor(private val editor: SharedPreferences.Editor) {
    fun <T> set(key: ConfigKey<T>, value: T): SettingEditor {
        key.setValue(editor, value)
        return this
    }

    fun apply() {
        editor.apply()
    }

    fun <T> remove(key: ConfigKey<T>): SettingEditor {
        editor.remove(key.name)
        return this
    }
}