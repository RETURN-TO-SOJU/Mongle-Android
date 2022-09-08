package com.won983212.mongle

import org.mockito.Mockito

object MockitoHelper {
    fun <T> anyClass(type: Class<T>): T = Mockito.any(type)

    fun <T> anyObject(): T {
        Mockito.any<T>()
        return uninitialized()
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> uninitialized(): T = null as T
}