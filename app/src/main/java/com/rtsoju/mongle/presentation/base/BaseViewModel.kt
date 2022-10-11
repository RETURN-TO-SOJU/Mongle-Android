package com.rtsoju.mongle.presentation.base

import androidx.activity.ComponentActivity
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rtsoju.mongle.presentation.util.SingleLiveEvent
import com.rtsoju.mongle.presentation.util.asLiveData
import com.rtsoju.mongle.presentation.util.toastShort
import com.rtsoju.mongle.presentation.view.dialog.LoadingDialog

open class BaseViewModel : ViewModel() {
    private val _eventErrorMessage = SingleLiveEvent<String>()
    val eventErrorMessage = _eventErrorMessage.asLiveData()

    private val _eventMessage = SingleLiveEvent<String>()
    val eventMessage = _eventMessage.asLiveData()

    private val _isLoading = MutableLiveData(false)
    val isLoading = _isLoading.asLiveData()

    private var loadingDialog: AlertDialog? = null


    protected fun setLoading(loading: Boolean) {
        _isLoading.postValue(loading)
    }

    protected fun showError(msg: String) {
        _eventErrorMessage.postValue(msg)
    }

    protected fun showMessage(msg: String) {
        _eventMessage.postValue(msg)
    }

    fun attachDefaultMessageHandler(context: ComponentActivity) {
        eventMessage.observe(context) {
            context.toastShort(it)
        }
    }

    fun attachDefaultErrorHandler(context: ComponentActivity) {
        eventErrorMessage.observe(context) {
            context.toastShort(it)
        }
    }

    fun attachDefaultLoadingHandler(context: ComponentActivity) {
        isLoading.observe(context) {
            if (it) {
                loadingDialog?.dismiss()
                loadingDialog = LoadingDialog(context).open()
            } else {
                loadingDialog?.dismiss()
            }
        }
    }

    fun attachDefaultHandlers(context: ComponentActivity) {
        attachDefaultMessageHandler(context)
        attachDefaultErrorHandler(context)
        attachDefaultLoadingHandler(context)
    }

    /**
     * Task 실행 중 **Loading처리** 및 **에러 처리**를 한다. (Viewmodel단에서 필요한 처리만)
     * 리턴값이 task결과를 얻을 수 있는 Result타입이어야 한다.
     * Task가 실패했다면 로딩 완료 처리 및 오류 메시지를 띄운다. 성공했다면 로딩만 완료처리.
     * @return task가 성공했다면 결과 값, 아니면 null (즉 getOrNull)
     */
    suspend fun <T> startProgressTask(task: suspend () -> Result<T>): T? {
        setLoading(true)
        val result = task()
        setLoading(false)

        result.onFailure {
            showError(it.message ?: "오류가 발생했습니다.")
        }

        return result.getOrNull()
    }

    /**
     * Task 실행 중 로딩 처리 없이 **에러 처리**만 한다. (Viewmodel단에서 필요한 처리만)
     * 리턴값이 task결과를 얻을 수 있는 Result타입이어야 한다.
     * Task가 실패했다면 오류 메시지를 띄운다. 성공했다면 아무런 처리도 안한다.
     * @return task가 성공했다면 결과 값, 아니면 null (즉 getOrNull)
     */
    suspend fun <T> startResultTask(task: suspend () -> Result<T>): T? {
        val result = task()
        result.onFailure {
            showError(it.message ?: "오류가 발생했습니다.")
        }
        return result.getOrNull()
    }

    /**
     * Task 실행 중 **Loading처리**를 한다. (Viewmodel단에서 필요한 처리만)
     * @return task의 결과 값
     */
    suspend fun <T> startTask(task: suspend () -> T): T {
        setLoading(true)
        val result = task()
        setLoading(false)
        return result
    }
}