package com.rtsoju.mongle.presentation.view.starting

import com.rtsoju.mongle.domain.usecase.password.HasScreenPasswordUseCase
import com.rtsoju.mongle.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StartingViewModel @Inject constructor(
    private val hasScreenPassword: HasScreenPasswordUseCase
) : BaseViewModel() {

    init {
        setLoading(true)
    }

    fun needsPasswordAuth(): Boolean = hasScreenPassword()
}