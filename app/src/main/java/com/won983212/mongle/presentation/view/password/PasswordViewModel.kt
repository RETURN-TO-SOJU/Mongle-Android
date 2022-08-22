package com.won983212.mongle.presentation.view.password

import com.won983212.mongle.domain.repository.PasswordRepository
import com.won983212.mongle.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PasswordViewModel @Inject constructor(
    private val passwordRepository: PasswordRepository
) : BaseViewModel() {

    fun checkPassword(password: String): Boolean = passwordRepository.getPassword() == password

    fun setPassword(password: String?) = passwordRepository.setPassword(password)
}