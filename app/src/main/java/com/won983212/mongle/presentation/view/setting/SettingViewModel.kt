package com.won983212.mongle.presentation.view.setting

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.won983212.mongle.R
import com.won983212.mongle.common.util.asLiveData
import com.won983212.mongle.domain.repository.UserRepository
import com.won983212.mongle.presentation.base.BaseViewModel
import com.won983212.mongle.presentation.util.TextResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {
    val isAlertEnabled = MutableLiveData(true)

    private val _usernameTitle = MutableLiveData(TextResource())
    val usernameTitle = _usernameTitle.asLiveData()

    fun updateUsernameTitle() = viewModelScope.launch {
        val user = userRepository.getUserInfo(this@SettingViewModel)
        var username = "??"
        if (user != null) {
            username = user.name
        }
        _usernameTitle.postValue(TextResource(R.string.setting_title_1, username))
    }
}