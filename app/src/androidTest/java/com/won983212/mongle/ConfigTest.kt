package com.won983212.mongle

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.won983212.mongle.data.repository.ConfigRepositoryImpl
import com.won983212.mongle.data.source.local.config.*
import com.won983212.mongle.domain.repository.ConfigRepository
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
internal class ConfigTest {

    private val configDataSource = ConfigDataSource(ApplicationProvider.getApplicationContext())
    private val configRepository: ConfigRepository = ConfigRepositoryImpl(configDataSource)

    private val stringKey = StringConfigKey("stringConfigKey")
    private val intKey = IntConfigKey("intConfigKey")
    private val booleanKey = BooleanConfigKey("booleanConfigKey")
    private val floatKey = FloatConfigKey("floatConfigKey")
    private val longKey = LongConfigKey("longConfigKey")

    @Test
    fun config_get_and_editor() {
        configRepository.editor().clear().apply()
        configRepository.editor()
            .set(stringKey, "hello")
            .set(intKey, 1234)
            .set(booleanKey, false)
            .set(floatKey, 12.1f)
            .set(longKey, 1872367812496)
            .apply()

        assertEquals("hello", configRepository.get(stringKey))
        assertEquals(1234, configRepository.get(intKey))
        assertEquals(false, configRepository.get(booleanKey))
        assertEquals(12.1f, configRepository.get(floatKey))
        assertEquals(1872367812496, configRepository.get(longKey))
    }

    // TODO 값설정 안되어있을 때 기본값으로 잘 설정하는지
    // 기본값마저 없을 때 throw가 잘 되는지
}