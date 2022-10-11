package com.rtsoju.mongle.presentation.view.agree

import androidx.lifecycle.MutableLiveData
import com.rtsoju.mongle.presentation.base.BaseViewModel
import com.rtsoju.mongle.presentation.util.asLiveData

class AgreeViewModel : BaseViewModel() {
    private val _agreeSelected = MutableLiveData(BooleanArray(4))
    val agreeSelected = _agreeSelected.asLiveData()

    private val _canMoveNext = MutableLiveData(false)
    val canMoveNext = _canMoveNext.asLiveData()

    private val _allSelected = MutableLiveData(false)
    val allSelected = _allSelected.asLiveData()

    private fun isAllChecked(): Boolean {
        agreeSelected.value?.forEach {
            if (!it) return false
        }
        return true
    }

    private fun notifyAgreeItemCheck() {
        val isAllAgreed = isAllChecked()
        _allSelected.value = isAllAgreed
        _canMoveNext.value = isAllAgreed
    }

    fun setAgreeChecked(at: Int, value: Boolean) {
        val newValue = _agreeSelected.value
        newValue?.set(at, value)
        _agreeSelected.value = newValue
        notifyAgreeItemCheck()
    }

    fun setAllAgreeChecked(value: Boolean) {
        _agreeSelected.value = BooleanArray(4) { value }
        notifyAgreeItemCheck()
    }
}