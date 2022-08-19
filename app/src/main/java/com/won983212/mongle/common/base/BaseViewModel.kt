package com.won983212.mongle.common.base

import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModel
import com.won983212.mongle.common.util.*

open class BaseViewModel : ViewModel(), NetworkErrorHandler {
    private val _eventErrorMessage = SingleLiveEvent<String>()
    val eventErrorMessage = _eventErrorMessage.asLiveData()

    override fun onNetworkError(errorType: ErrorType, msg: String) {
        onError(msg)
    }

    fun onError(msg: String) {
        _eventErrorMessage.postValue(msg)
    }

    fun attachToastErrorHandler(context: ComponentActivity) {
        eventErrorMessage.observe(context) {
            context.toastShort(it)
        }
    }
}