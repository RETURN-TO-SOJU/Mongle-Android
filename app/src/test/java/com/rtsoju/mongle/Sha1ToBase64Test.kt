package com.rtsoju.mongle

import com.google.common.truth.Truth
import org.junit.Test

class Sha1ToBase64Test {
    @Test
    fun convert() {
        val sha1 = "F8:97:E5:69:B9:82:B2:40:80:66:5C:6D:15:C1:E7:99:0B:B1:0D:AC"
        val result = java.util.Base64.getEncoder()
            .encodeToString(sha1.split(":").map { it.toInt(16).toByte() }
                .toByteArray())
        Truth.assertThat(result).isEqualTo("+JflabmCskCAZlxtFcHnmQuxDaw=")
    }
}