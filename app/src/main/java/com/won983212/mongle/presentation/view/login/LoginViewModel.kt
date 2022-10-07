package com.won983212.mongle.presentation.view.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.messaging.FirebaseMessaging
import com.kakao.sdk.auth.model.OAuthToken
import com.won983212.mongle.domain.model.OAuthLoginToken
import com.won983212.mongle.domain.usecase.auth.LoginUseCase
import com.won983212.mongle.domain.usecase.auth.ValidateTokenUseCase
import com.won983212.mongle.domain.usecase.user.SetFCMTokenUseCase
import com.won983212.mongle.presentation.base.BaseViewModel
import com.won983212.mongle.presentation.util.SingleLiveEvent
import com.won983212.mongle.presentation.util.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val setFCMToken: SetFCMTokenUseCase,
    private val login: LoginUseCase,
    private val validateToken: ValidateTokenUseCase
) : BaseViewModel() {

    private val _eventReadyForRegister = SingleLiveEvent<Unit>()
    val eventReadyForRegister = _eventReadyForRegister.asLiveData()

    private val _eventLoggedIn = SingleLiveEvent<Unit>()
    val eventLoggedIn = _eventLoggedIn.asLiveData()

    private val _loginEnabled = MutableLiveData(false)
    val loginEnabled = _loginEnabled.asLiveData()


    fun doLoginWithKakaoToken(token: OAuthToken) = viewModelScope.launch(Dispatchers.IO) {
        val response = startProgressTask {
            login(OAuthLoginToken.fromKakaoToken(token))
        }
        if (response != null) {
            FirebaseMessaging.getInstance().token.addOnSuccessListener { result ->
                viewModelScope.launch(Dispatchers.IO) {
                    setFCMToken(result)
                }
            }
            if (response.isNew || response.name.isEmpty()) {
                _eventReadyForRegister.post()
            } else {
                _eventLoggedIn.post()
            }
        }
    }

    fun checkCanAutoLogin() = viewModelScope.launch(Dispatchers.IO) {
        val canAutoLogin = startTask {
            validateToken()
        }
        if (canAutoLogin) {
            _eventLoggedIn.post()
        }
        _loginEnabled.postValue(true)
    }
}