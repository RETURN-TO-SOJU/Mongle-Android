package com.rtsoju.mongle

import android.content.res.Resources
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.rtsoju.mongle.data.source.local.config.*
import com.rtsoju.mongle.data.repository.ConfigRepositoryImpl
import com.rtsoju.mongle.domain.repository.ConfigRepository
import com.rtsoju.mongle.R
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
internal class ConfigTest {

    private val configDataSource = ConfigDataSource(
        ApplicationProvider.getApplicationContext(),
        ConfigDataSource.ResourceContext(
            R.xml.settings_test,
            R.styleable.settings,
            R.styleable.settings_name,
            R.styleable.settings_defaultValue
        )
    )
    private val configRepository: ConfigRepository = ConfigRepositoryImpl(configDataSource)

    private val stringKey = StringConfigKey("stringConfigKey")
    private val intKey = IntConfigKey("intConfigKey")
    private val booleanKey = BooleanConfigKey("booleanConfigKey")
    private val floatKey = FloatConfigKey("floatConfigKey")
    private val longKey = LongConfigKey("longConfigKey")

    private val wrongIntKey = IntConfigKey("wrongIntConfigKey")
    private val unknownKey = StringConfigKey("unknownConfigKey")

    @Before
    fun before() {
        configRepository.editor().clear().apply()
    }

    @Test
    fun config_get_and_editor() {
        configRepository.editor()
            .set(stringKey, "he1llo")
            .set(intKey, 12314)
            .set(booleanKey, true)
            .set(floatKey, 12.1f)
            .set(longKey, 1241254125125)
            .apply()

        assertThat(configRepository.get(stringKey)).isEqualTo("he1llo")
        assertThat(configRepository.get(intKey)).isEqualTo(12314)
        assertThat(configRepository.get(booleanKey)).isEqualTo(true)
        assertThat(configRepository.get(floatKey)).isEqualTo(12.1f)
        assertThat(configRepository.get(longKey)).isEqualTo(1241254125125)
    }

    @Test
    fun config_get_default() {
        assertThat(configRepository.get(stringKey)).isEqualTo("STRING")
        assertThat(configRepository.get(intKey)).isEqualTo(12)
        assertThat(configRepository.get(booleanKey)).isEqualTo(false)
        assertThat(configRepository.get(floatKey)).isEqualTo(12.5f)
        assertThat(configRepository.get(longKey)).isEqualTo(1872367812496)
    }

    @Test
    fun config_get_unknown_default_key() {
        try {
            configRepository.get(unknownKey)
        } catch (e: java.lang.Exception) {
            assertThat(e).isInstanceOf(Resources.NotFoundException::class.java)
        }
    }
}