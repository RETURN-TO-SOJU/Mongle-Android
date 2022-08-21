package com.won983212.mongle.presentation.view.login

import androidx.lifecycle.viewModelScope
import com.kakao.sdk.auth.model.OAuthToken
import com.won983212.mongle.common.util.asLiveData
import com.won983212.mongle.data.model.OAuthLoginToken
import com.won983212.mongle.domain.repository.UserRepository
import com.won983212.mongle.presentation.base.BaseViewModel
import com.won983212.mongle.presentation.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {

    private val _eventLoggedIn = SingleLiveEvent<Unit>()
    val eventLoggedIn = _eventLoggedIn.asLiveData()

    fun doLoginWithKakaoToken(token: OAuthToken) = viewModelScope.launch {
        val response = userRepository.login(this@LoginViewModel, OAuthLoginToken.of(token))
        if (response != null) {
            userRepository.setCurrentToken(response)
            _eventLoggedIn.call()
        }
    }
}