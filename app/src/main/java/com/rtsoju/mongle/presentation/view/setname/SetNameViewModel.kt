package com.rtsoju.mongle.presentation.view.setname

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.rtsoju.mongle.domain.usecase.user.SetUsernameUseCase
import com.rtsoju.mongle.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SetNameViewModel @Inject constructor(
    private val setUsername: SetUsernameUseCase
) : BaseViewModel() {

    val name = MutableLiveData("")

    val canMoveNext = Transformations.map(name) {
        it.isNotBlank()
    }

    fun updateUsername() = viewModelScope.launch(Dispatchers.IO) {
        val nameText = name.value
        if (nameText != null) {
            startProgressTask { setUsername(nameText) }
        } else {
            showError("이름을 제대로 입력해주세요.")
        }
    }
}