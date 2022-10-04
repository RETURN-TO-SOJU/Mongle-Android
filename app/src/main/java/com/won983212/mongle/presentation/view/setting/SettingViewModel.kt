package com.won983212.mongle.presentation.view.setting

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.won983212.mongle.R
import com.won983212.mongle.data.source.local.config.ConfigKey
import com.won983212.mongle.domain.usecase.config.EditConfigUseCase
import com.won983212.mongle.domain.usecase.config.GetConfigUseCase
import com.won983212.mongle.domain.usecase.password.SetDataKeyPasswordUseCase
import com.won983212.mongle.domain.usecase.user.GetUserInfoUseCase
import com.won983212.mongle.domain.usecase.user.LeaveAccountUseCase
import com.won983212.mongle.presentation.base.BaseViewModel
import com.won983212.mongle.presentation.util.SingleLiveEvent
import com.won983212.mongle.presentation.util.TextResource
import com.won983212.mongle.presentation.util.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    getConfig: GetConfigUseCase,
    editConfig: EditConfigUseCase,
    private val getUserInfo: GetUserInfoUseCase,
    private val leaveAccount: LeaveAccountUseCase,
    private val setDataKeyPassword: SetDataKeyPasswordUseCase
) : BaseViewModel() {
    val isAlertEnabled = MutableLiveData(true)

    private val _usernameTitle = MutableLiveData(TextResource())
    val usernameTitle = _usernameTitle.asLiveData()

    private val _eventLeaveAccount = SingleLiveEvent<Unit>()
    val eventLeaveAccount = _eventLeaveAccount.asLiveData()

    init {
        isAlertEnabled.value = getConfig(ConfigKey.USE_ALERT)
        isAlertEnabled.observeForever {
            editConfig().set(ConfigKey.USE_ALERT, it).apply()
        }
    }

    fun setPasswordTo(password: String) {
        setDataKeyPassword(password)
    }

    fun updateUsernameTitle() = viewModelScope.launch(Dispatchers.IO) {
        val user = startProgressTask { getUserInfo() }
        var username = "??"
        if (user != null) {
            username = user.username
        }
        _usernameTitle.postValue(TextResource(R.string.setting_title_1, username))
    }

    fun doLeave() = viewModelScope.launch(Dispatchers.IO) {
        val result = startProgressTask { leaveAccount() }
        if (result != null) {
            _eventLeaveAccount.post()
        }
    }
}