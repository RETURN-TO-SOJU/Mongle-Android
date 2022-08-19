package com.won983212.mongle.view.login

import androidx.lifecycle.viewModelScope
import com.kakao.sdk.auth.model.OAuthToken
import com.won983212.mongle.common.base.BaseViewModel
import com.won983212.mongle.common.model.OAuthLoginToken
import com.won983212.mongle.common.util.SingleLiveEvent
import com.won983212.mongle.common.util.asLiveData
import com.won983212.mongle.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel() {

    private val _eventLoggedIn = SingleLiveEvent<Unit>()
    val eventLoggedIn = _eventLoggedIn.asLiveData()

    fun doLoginWithKakaoToken(token: OAuthToken) = viewModelScope.launch {
        val response = authRepository.login(this@LoginViewModel, OAuthLoginToken.of(token))
        if (response != null) {
            authRepository.setCurrentToken(response)
            _eventLoggedIn.call()
        }
    }
}