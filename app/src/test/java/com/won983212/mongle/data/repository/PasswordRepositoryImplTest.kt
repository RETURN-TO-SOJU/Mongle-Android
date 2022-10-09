package com.won983212.mongle.data.repository

import android.util.Base64
import com.google.common.truth.Truth.assertThat
import com.won983212.mongle.data.source.PasswordDataSource
import com.won983212.mongle.domain.repository.PasswordRepository
import com.won983212.mongle.util.generateFillZero
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mockStatic
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
            java.util.Base64.getDecoder().decode(data)
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

    @Test
    fun setDataKeyPasswordLessLen() {
        val pwd = "abcd"
        repository.setDataKeyPassword(pwd)
        val expect = pwd + generateFillZero(PasswordRepositoryImpl.PASSWORD_LEN - pwd.length)
        assertThat(dataSource.getDataKeyPassword()).isEqualTo(expect)
    }

    @Test
    fun setDataKeyPasswordGreaterLen() {
        val pwd = "abcd1234567812345678123456781234567812345678"
        repository.setDataKeyPassword(pwd)
        val expect = pwd.substring(0, PasswordRepositoryImpl.PASSWORD_LEN)
        assertThat(dataSource.getDataKeyPassword()).isEqualTo(expect)
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