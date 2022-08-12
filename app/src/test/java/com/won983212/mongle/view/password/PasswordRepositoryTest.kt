package com.won983212.mongle.view.password

import org.junit.Assert
import org.junit.Test

internal class PasswordRepositoryTest {

    @Test
    fun push_digits() {
        for (len in 1..10) {
            val repo = PasswordMemory(len)
            val actualPwd = StringBuilder()
            for (i in 1 until len) {
                actualPwd.append(i.toChar())
                Assert.assertEquals(i, repo.pushDigit(i.toChar()))
            }
            repo.setOnFullListener {
                Assert.assertEquals(actualPwd.toString(), it)
            }
            actualPwd.append('1')
            Assert.assertEquals(0, repo.pushDigit('1'))
        }
    }

    @Test
    fun remove_digits() {
        for (len in 2..10) {
            val repo = PasswordMemory(len)
            val actualPwd = StringBuilder()
            for (i in 1 until len) {
                if (i < len - 1) {
                    actualPwd.append(i.toChar())
                }
                Assert.assertEquals(i, repo.pushDigit(i.toChar()))
            }
            actualPwd.append("11")
            repo.setOnFullListener {
                Assert.assertEquals(actualPwd.toString(), it)
            }
            Assert.assertEquals(repo.popDigit(), len - 2)
            Assert.assertEquals(repo.pushDigit('1'), len - 1)
            Assert.assertEquals(repo.pushDigit('1'), 0)
        }
    }
}