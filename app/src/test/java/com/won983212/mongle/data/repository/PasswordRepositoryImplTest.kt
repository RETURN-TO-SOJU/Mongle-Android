package com.won983212.mongle.data.repository

import android.util.Base64
import com.won983212.mongle.data.source.PasswordDataSource
import com.won983212.mongle.domain.repository.PasswordRepository
import org.assertj.core.api.Assertions.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import java.io.ByteArrayInputStream


internal class PasswordRepositoryImplTest {
    private lateinit var dataSource: PasswordDataSource
    private lateinit var repository: PasswordRepository

    @Before
    fun before() {
        dataSource = MockPasswordDataSource()
        repository = PasswordRepositoryImpl(dataSource)
    }

    @Test
    fun decryptByKeyPassword() {
        val base64Mock = mockStatic(Base64::class.java)
        val data = "nKYmFyQ0zH+xchWeMKlruZW2dxqjukNmWwAsnZvm3wzkOyHzNMi" +
                "lkbAokihasjDjIsssnI3hW3g0c6LUmQxIJSI9yM8LTOWD+RQLg044B" +
                "o6OcHUKjSDWIt+KCG/772f7F+2u+RSSYEXrnfo0BTqVTCNHf/ZqHOS" +
                "Gyjdfv84XGTMluNvKPWfyqE8IQ8zxDQW4Gjfh4ix22jh18aekI9h2Uw=="

        `when`(Base64.decode(data, 0)).thenReturn(
            byteArrayOf(
                -100,
                -90,
                38,
                23,
                36,
                52,
                -52,
                127,
                -79,
                114,
                21,
                -98,
                48,
                -87,
                107,
                -71,
                -107,
                -74,
                119,
                26,
                -93,
                -70,
                67,
                102,
                91,
                0,
                44,
                -99,
                -101,
                -26,
                -33,
                12,
                -28,
                59,
                33,
                -13,
                52,
                -56,
                -91,
                -111,
                -80,
                40,
                -110,
                40,
                90,
                -78,
                48,
                -29,
                34,
                -53,
                44,
                -100,
                -115,
                -31,
                91,
                120,
                52,
                115,
                -94,
                -44,
                -103,
                12,
                72,
                37,
                34,
                61,
                -56,
                -49,
                11,
                76,
                -27,
                -125,
                -7,
                20,
                11,
                -125,
                78,
                56,
                6,
                -114,
                -114,
                112,
                117,
                10,
                -115,
                32,
                -42,
                34,
                -33,
                -118,
                8,
                111,
                -5,
                -17,
                103,
                -5,
                23,
                -19,
                -82,
                -7,
                20,
                -110,
                96,
                69,
                -21,
                -99,
                -6,
                52,
                5,
                58,
                -107,
                76,
                35,
                71,
                127,
                -10,
                106,
                28,
                -28,
                -122,
                -54,
                55,
                95,
                -65,
                -50,
                23,
                25,
                51,
                37,
                -72,
                -37,
                -54,
                61,
                103,
                -14,
                -88,
                79,
                8,
                67,
                -52,
                -15,
                13,
                5,
                -72,
                26,
                55,
                -31,
                -30,
                44,
                118,
                -38,
                56,
                117,
                -15,
                -89,
                -92,
                35,
                -40,
                118,
                83
            )
        )
        dataSource.setDataKeyPassword("oingisprettyintheworld1234567890")

        val result = repository.decryptByKeyPassword(data)
        assertThat(result)
            .isEqualTo("ㅋㅋㅋㅋㅋㅋㅋ 아... 벤치에서 술마신거까진 기억하는데 일어나보니 버스안이라 당황했네요 ㅋㅋㅋㅋ")

        base64Mock.close()
    }

    @Test
    fun makePwdKakaotalkDataPacket() {
        dataSource.setDataKeyPassword("oingisprettyintheworld1234567890")

        val text = "Hello, world!\nNice to meet you."
        val bais = ByteArrayInputStream(text.toByteArray(PasswordRepositoryImpl.PASSWORD_CHARSET))
        val packet = repository.makePwdKakaotalkDataPacket(bais)
        bais.close()

        val result = String(packet, PasswordRepositoryImpl.PASSWORD_CHARSET)
        val expect = "${text}\n${dataSource.getDataKeyPassword()}"
        assertThat(result).isEqualTo(expect)
    }

    class MockPasswordDataSource : PasswordDataSource {
        private var screenPwd: String? = null
        private var keyPwd: String? = null

        override fun getScreenPassword(): String? {
            return screenPwd
        }

        override fun setScreenPassword(password: String?) {
            screenPwd = password
        }

        override fun getDataKeyPassword(): String? {
            return keyPwd
        }

        override fun setDataKeyPassword(password: String?) {
            keyPwd = password
        }
    }
}