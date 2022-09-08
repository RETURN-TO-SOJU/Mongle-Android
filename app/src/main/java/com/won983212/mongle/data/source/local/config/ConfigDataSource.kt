package com.won983212.mongle.data.source.local.config

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import com.won983212.mongle.R
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.internal.toImmutableMap
import org.xmlpull.v1.XmlPullParser
import javax.inject.Inject

internal class ConfigDataSource @Inject constructor(
    @ApplicationContext
    private val context: Context
) {
    private val defaultValues by lazy {
        readDefaultValues(context)
    }

    private val preferences: SharedPreferences =
        context.getSharedPreferences(CONFIG_PREFERENCE_NAME, Context.MODE_PRIVATE)


    private fun readDefaultValues(context: Context): Map<String, String> {
        var eventType = -1
        val parser = context.resources.getXml(R.xml.settings)
        val defaultValues = mutableMapOf<String, String>()

        while (eventType != XmlPullParser.END_DOCUMENT) {
            eventType = parser.next()
            if (eventType == XmlPullParser.START_TAG) {
                if (parser.name != "item") {
                    continue
                }

                val attrs = context.resources.obtainAttributes(parser, R.styleable.settings)
                val name = attrs.getString(R.styleable.settings_name) ?: ""
                val defaultValue = attrs.getString(R.styleable.settings_defaultValue) ?: ""

                attrs.recycle()
                defaultValues[name] = defaultValue
            }
        }

        return defaultValues.toImmutableMap()
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> get(key: ConfigKey<T>): T {
        val result: T
        if (preferences.contains(key.name)) {
            result = key.getValue(preferences)
        } else {
            val defaultValueStr = defaultValues[key.name]
                ?: throw Resources.NotFoundException("Default value of ${key.name} is not defined in settings.xml")
            result = key.deserialize(defaultValueStr)
            editor().set(key, result).apply()
        }
        return result
    }

    fun editor(): SettingEditor = SettingEditor(preferences.edit())

    companion object {
        private const val CONFIG_PREFERENCE_NAME = "cfg_pref"
    }
}