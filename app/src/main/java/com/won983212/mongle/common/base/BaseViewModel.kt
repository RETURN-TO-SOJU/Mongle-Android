package com.won983212.mongle.common.base

import androidx.activity.ComponentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.won983212.mongle.common.util.*

open class BaseViewModel : ViewModel(), NetworkErrorHandler {
    private val _eventErrorMessage = SingleLiveEvent<String>()
    val eventErrorMessage = _eventErrorMessage.asLiveData()

    private val _isLoading = MutableLiveData(false)
    val isLoading = _isLoading.asLiveData()


    protected fun setLoading(loading: Boolean) {
        _isLoading.postValue(loading)
    }

    protected fun setError(msg: String) {
        _eventErrorMessage.postValue(msg)
    }

    fun attachToastErrorHandler(context: ComponentActivity) {
        eventErrorMessage.observe(context) {
            context.toastShort(it)
        }
    }

    override fun onNetworkError(errorType: ErrorType, msg: String) {
        setError(msg)
    }
}