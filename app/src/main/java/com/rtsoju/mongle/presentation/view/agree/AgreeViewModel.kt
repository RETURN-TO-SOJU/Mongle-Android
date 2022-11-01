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

    /**
     * 선택 항목(index 3)를 제외한 나머지가 모두 체크되어있다면 true반환
     */
    private fun isRequiresAllChecked(): Boolean {
        agreeSelected.value?.take(3)?.forEach {
            if (!it) return false
        }
        return true
    }

    private fun notifyAgreeItemCheck() {
        val isRequiresAllChecked = isRequiresAllChecked()
        _allSelected.value = isRequiresAllChecked
        _canMoveNext.value = isRequiresAllChecked
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