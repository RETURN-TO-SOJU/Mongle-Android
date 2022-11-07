package com.rtsoju.mongle.data.source.local.config

import android.content.SharedPreferences

sealed class ConfigKey<T>(val name: String) {
    abstract fun deserialize(value: String): T
    abstract fun getValue(preferences: SharedPreferences): T
    abstract fun setValue(editor: SharedPreferences.Editor, value: T)

    companion object {
        val USE_ALERT = BooleanConfigKey("useAlert")
        val WATCHED_DETAIL_SHOWCASE = BooleanConfigKey("watchedDetailShowcase")
    }
}

class BooleanConfigKey(name: String) : ConfigKey<Boolean>(name) {
    override fun deserialize(value: String): Boolean = value.toBoolean()

    override fun getValue(preferences: SharedPreferences): Boolean {
        return preferences.getBoolean(name, true)
    }

    override fun setValue(editor: SharedPreferences.Editor, value: Boolean) {
        editor.putBoolean(name, value)
    }
}

class IntConfigKey(name: String) : ConfigKey<Int>(name) {
    override fun deserialize(value: String): Int = value.toInt()
    override fun getValue(preferences: SharedPreferences): Int {
        return preferences.getInt(name, 0)
    }

    override fun setValue(editor: SharedPreferences.Editor, value: Int) {
        editor.putInt(name, value)
    }
}

class StringConfigKey(name: String) : ConfigKey<String>(name) {
    override fun deserialize(value: String): String = value
    override fun getValue(preferences: SharedPreferences): String {
        return preferences.getString(name, null) ?: ""
    }

    override fun setValue(editor: SharedPreferences.Editor, value: String) {
        editor.putString(name, value)
    }
}

class FloatConfigKey(name: String) : ConfigKey<Float>(name) {
    override fun deserialize(value: String): Float = value.toFloat()
    override fun getValue(preferences: SharedPreferences): Float {
        return preferences.getFloat(name, 0f)
    }

    override fun setValue(editor: SharedPreferences.Editor, value: Float) {
        editor.putFloat(name, value)
    }
}

class LongConfigKey(name: String) : ConfigKey<Long>(name) {
    override fun deserialize(value: String): Long = value.toLong()
    override fun getValue(preferences: SharedPreferences): Long {
        return preferences.getLong(name, 0)
    }

    override fun setValue(editor: SharedPreferences.Editor, value: Long) {
        editor.putLong(name, value)
    }
}