package com.won983212.mongle.util

import com.google.common.truth.Truth

fun <T> testSuccessResult(result: Result<T>, testSequence: (T) -> Unit) {
    Truth.assertThat(result.isSuccess).isEqualTo(true)
    result.onSuccess {
        testSequence(it)
    }
}

fun <T> testFailedResult(result: Result<T>, testSequence: (Throwable) -> Unit) {
    Truth.assertThat(result.isFailure).isEqualTo(true)
    result.onFailure {
        testSequence(it)
    }
}