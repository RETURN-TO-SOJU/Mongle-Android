package com.won983212.mongle.presentation.view.namesetup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.won983212.mongle.data.source.api.RequestErrorType
import com.won983212.mongle.domain.repository.UserRepository
import com.won983212.mongle.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NameSettingViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {

    val name = MutableLiveData("")

    val canMoveNext = Transformations.map(name) {
        it.isNotBlank()
    }

    fun updateUsername() = viewModelScope.launch {
        val nameText = name.value
        if (nameText != null) {
            userRepository.setUsername(this@NameSettingViewModel, nameText)
        } else {
            onError(RequestErrorType.UNKNOWN, "이름을 제대로 입력해주세요.")
        }
    }
}