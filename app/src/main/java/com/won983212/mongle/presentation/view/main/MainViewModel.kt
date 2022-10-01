package com.won983212.mongle.presentation.view.main

import androidx.lifecycle.ViewModel
import com.won983212.mongle.domain.repository.PasswordRepository
import com.won983212.mongle.presentation.util.SingleLiveEvent
import com.won983212.mongle.presentation.util.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val passwordRepository: PasswordRepository
) : ViewModel() {

    private val _eventEmptyPassword = SingleLiveEvent<Unit>()
    val eventEmptyPassword = _eventEmptyPassword.asLiveData()

    fun checkIfPasswordEmpty() {
        if (!passwordRepository.hasDataKeyPassword()) {
            _eventEmptyPassword.call()
        }
    }

    fun setPasswordTo(password: String) {
        passwordRepository.setDataKeyPassword(password)
    }
}