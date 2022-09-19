package com.won983212.mongle.presentation.view.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.messaging.FirebaseMessaging
import com.kakao.sdk.auth.model.OAuthToken
import com.won983212.mongle.presentation.util.asLiveData
import com.won983212.mongle.data.model.OAuthLoginToken
import com.won983212.mongle.domain.repository.AuthRepository
import com.won983212.mongle.domain.repository.UserRepository
import com.won983212.mongle.domain.usecase.ValidateTokenUseCase
import com.won983212.mongle.presentation.base.BaseViewModel
import com.won983212.mongle.presentation.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository,
    private val validateTokenUseCase: ValidateTokenUseCase
) : BaseViewModel() {

    private val _eventReadyForRegister = SingleLiveEvent<Unit>()
    val eventReadyForRegister = _eventReadyForRegister.asLiveData()

    private val _eventLoggedIn = SingleLiveEvent<Unit>()
    val eventLoggedIn = _eventLoggedIn.asLiveData()

    private val _loginEnabled = MutableLiveData(false)
    val loginEnabled = _loginEnabled.asLiveData()


    fun doLoginWithKakaoToken(token: OAuthToken) = viewModelScope.launch(Dispatchers.IO) {
        val response = startProgressTask {
            authRepository.login(OAuthLoginToken.fromKakaoToken(token))
        }
        if (response != null) {
            FirebaseMessaging.getInstance().token.addOnSuccessListener { result ->
                viewModelScope.launch(Dispatchers.IO) {
                    userRepository.setFCMToken(result)
                }
            }
            if (response.isNew) {
                _eventReadyForRegister.post()
            } else {
                _eventLoggedIn.post()
            }
        }
    }

    fun checkCanAutoLogin() = viewModelScope.launch(Dispatchers.IO) {
        val canAutoLogin = startTask {
            validateTokenUseCase.execute()
        }
        if (canAutoLogin) {
            _eventLoggedIn.post()
        }
        _loginEnabled.postValue(true)
    }
}