package com.won983212.mongle.presentation.view.starting

import com.won983212.mongle.domain.repository.PasswordRepository
import com.won983212.mongle.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StartingViewModel @Inject constructor(
    private val passwordRepository: PasswordRepository
) : BaseViewModel() {

    init {
        setLoading(true)
    }

    fun needsPasswordAuth(): Boolean = passwordRepository.getPassword() != null
}