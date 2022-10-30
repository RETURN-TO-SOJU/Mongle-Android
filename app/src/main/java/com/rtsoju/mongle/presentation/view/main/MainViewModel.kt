package com.rtsoju.mongle.presentation.view.main

import androidx.lifecycle.ViewModel
import com.rtsoju.mongle.domain.usecase.calendar.GetCalendarDayDetailUseCase
import com.rtsoju.mongle.domain.usecase.calendar.GetCalendarDayMetadataUseCase
import com.rtsoju.mongle.domain.usecase.password.HasDataKeyPasswordUseCase
import com.rtsoju.mongle.domain.usecase.password.SetDataKeyPasswordUseCase
import com.rtsoju.mongle.presentation.util.SingleLiveEvent
import com.rtsoju.mongle.presentation.util.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val hasDataKeyPassword: HasDataKeyPasswordUseCase,
    private val setDataKeyPassword: SetDataKeyPasswordUseCase
) : ViewModel() {

    private val _eventEmptyPassword = SingleLiveEvent<Unit>()
    val eventEmptyPassword = _eventEmptyPassword.asLiveData()

    fun checkIfPasswordEmpty() {
        if (!hasDataKeyPassword()) {
            _eventEmptyPassword.call()
        }
    }

    fun setPasswordTo(password: String) {
        setDataKeyPassword(password)
    }
}