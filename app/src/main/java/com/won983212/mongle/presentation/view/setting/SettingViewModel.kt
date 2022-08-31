package com.won983212.mongle.presentation.view.setting

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.won983212.mongle.R
import com.won983212.mongle.common.util.asLiveData
import com.won983212.mongle.domain.repository.ConfigRepository
import com.won983212.mongle.domain.repository.UserRepository
import com.won983212.mongle.presentation.base.BaseViewModel
import com.won983212.mongle.presentation.util.SingleLiveEvent
import com.won983212.mongle.presentation.util.TextResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val configRepository: ConfigRepository
) : BaseViewModel() {
    val isAlertEnabled = MutableLiveData(true)

    private val _usernameTitle = MutableLiveData(TextResource())
    val usernameTitle = _usernameTitle.asLiveData()

    private val _eventLeaveAccount = SingleLiveEvent<Unit>()
    val eventLeaveAccount = _eventLeaveAccount.asLiveData()

    init {
        // TODO Refactor
        isAlertEnabled.value = configRepository.get().getBoolean("useAlert", true)
        isAlertEnabled.observeForever {
            configRepository.get().edit()
                .putBoolean("useAlert", it)
                .apply()
        }
    }

    fun updateUsernameTitle() = viewModelScope.launch {
        val user = startProgressTask { userRepository.getUserInfo() }
        var username = "??"
        if (user != null) {
            username = user.username
        }
        _usernameTitle.postValue(TextResource(R.string.setting_title_1, username))
    }

    fun doLeave() = viewModelScope.launch {
        val result = startProgressTask { userRepository.leaveAccount() }
        if (result != null) {
            _eventLeaveAccount.call()
        }
    }
}