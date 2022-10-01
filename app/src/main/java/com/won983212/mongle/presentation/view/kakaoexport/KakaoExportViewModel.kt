package com.won983212.mongle.presentation.view.kakaoexport

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.won983212.mongle.domain.repository.KakaotalkRepository
import com.won983212.mongle.domain.repository.PasswordRepository
import com.won983212.mongle.presentation.base.BaseViewModel
import com.won983212.mongle.presentation.util.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.InputStream
import javax.inject.Inject

@HiltViewModel
class KakaoExportViewModel @Inject constructor(
    private val kakaotalkRepository: KakaotalkRepository,
    private val passwordRepository: PasswordRepository
) : BaseViewModel() {

    private val _isAnalyzing = MutableLiveData(false)
    val isAnalyzing = _isAnalyzing.asLiveData()

    fun uploadKakaotalk(roomName: String, stream: InputStream) =
        viewModelScope.launch(Dispatchers.IO) {
            val response = startProgressTask {
                kakaotalkRepository.upload(
                    roomName,
                    passwordRepository.makePwdKakaotalkDataPacket(stream)
                )
            }
            if (response != null) {
                _isAnalyzing.postValue(true)
            }
        }
}