package com.rtsoju.mongle.presentation.view.password

import android.content.Intent
import androidx.lifecycle.MutableLiveData
import com.rtsoju.mongle.R
import com.rtsoju.mongle.domain.usecase.password.CheckScreenPasswordUseCase
import com.rtsoju.mongle.domain.usecase.password.SetScreenPasswordUseCase
import com.rtsoju.mongle.presentation.base.BaseViewModel
import com.rtsoju.mongle.presentation.util.SingleLiveEvent
import com.rtsoju.mongle.presentation.util.asLiveData
import com.rtsoju.mongle.presentation.util.getSerializableExtraCompat
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PasswordViewModel @Inject constructor(
    private val setScreenPassword: SetScreenPasswordUseCase,
    private val checkScreenPassword: CheckScreenPasswordUseCase
) : BaseViewModel(), PasswordInputListener {

    private val _title = MutableLiveData(0)
    val title = _title.asLiveData()

    private val _subtitle = MutableLiveData(0)
    val subtitle = _subtitle.asLiveData()

    private val _actionBtnText = MutableLiveData(0)
    val actionBtnText = _actionBtnText.asLiveData()

    private val _eventAuthFinish = SingleLiveEvent<Unit>()
    val eventAuthFinish = _eventAuthFinish.asLiveData()

    private val _eventFail = SingleLiveEvent<Int>()
    val eventFail = _eventFail.asLiveData()

    private val _eventPwdIndicatorStateChanged = SingleLiveEvent<Pair<Int, Boolean>>()

    /**
     * **Parameter**: Pair<Index, CheckState>
     *
     * Index에 있는 indicator의 check state가 변경되었을 때 호출됨.
     * 만약 Index가 [INDICATOR_INDEX_ALL]이면 모든 indicator를 지칭함
     */
    val eventPwdIndicatorStateChanged = _eventPwdIndicatorStateChanged.asLiveData()

    private var pwdPrevInput: String = ""
    private val pwdMemory = PasswordMemory(4)
    private var mode = PasswordActivity.Mode.AUTH


    fun initializeByIntent(intent: Intent) {
        mode = intent.getSerializableExtraCompat(
            PasswordActivity.EXTRA_MODE,
            PasswordActivity.Mode.AUTH
        )
        pwdMemory.setOnFullListener(this)
        setupUIByMode()
    }

    /**
     * 사용자가 상단의 액션 버튼을 클릭했을 때의 처리. mode에 따라 처리가 다르다.
     */
    fun doAction() {
        when (mode) {
            PasswordActivity.Mode.SET -> {
                setScreenPassword(null)
                _eventAuthFinish.call()
            }
            PasswordActivity.Mode.AUTH -> {
            }
            else -> {}
        }
    }

    private fun setupUIByMode() {
        when (mode) {
            PasswordActivity.Mode.SET -> {
                _title.value = R.string.pwd_set_title
                _subtitle.value = R.string.pwd_set_subtitle
                _actionBtnText.value = R.string.pwd_delete
            }
            PasswordActivity.Mode.REENTER -> {
                _title.value = R.string.pwd_reenter_title
                _subtitle.value = R.string.pwd_reenter_subtitle
            }
            PasswordActivity.Mode.AUTH -> {
                _title.value = R.string.pwd_auth_title
                _subtitle.value = 0
                // TODO (LATER) 암호분실 기능은 향후에 구현
                // _actionBtnText.value = R.string.pwd_lost
                _actionBtnText.value = 0
            }
        }
    }

    override fun onPasswordInput(password: String) {
        when (mode) {
            PasswordActivity.Mode.AUTH -> {
                if (checkScreenPassword(password)) {
                    _eventAuthFinish.call()
                } else {
                    _eventFail.value = R.string.pwd_wrong
                }
            }
            PasswordActivity.Mode.REENTER -> {
                if (pwdPrevInput == password) {
                    setScreenPassword(password)
                    _eventAuthFinish.call()
                } else {
                    _eventFail.value = R.string.pwd_not_matched
                    mode = PasswordActivity.Mode.SET
                    setupUIByMode()
                }
            }
            PasswordActivity.Mode.SET -> {
                pwdPrevInput = password
                mode = PasswordActivity.Mode.REENTER
                setupUIByMode()
            }
        }
    }

    fun appendPassword(digit: Char) {
        val length = pwdMemory.pushDigit(digit)
        if (length > 0) {
            _eventPwdIndicatorStateChanged.value = (length - 1) to true
        } else {
            _eventPwdIndicatorStateChanged.value = INDICATOR_INDEX_ALL to false
        }
    }

    fun removePassword() {
        val length = pwdMemory.popDigit()
        _eventPwdIndicatorStateChanged.value = length to false
    }

    companion object {
        const val INDICATOR_INDEX_ALL = -1
    }
}