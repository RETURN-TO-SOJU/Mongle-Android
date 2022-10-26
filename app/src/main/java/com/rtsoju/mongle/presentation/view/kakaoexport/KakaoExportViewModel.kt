package com.rtsoju.mongle.presentation.view.kakaoexport

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.rtsoju.mongle.domain.usecase.kakaotalk.UploadKakaotalkUseCase
import com.rtsoju.mongle.presentation.base.BaseViewModel
import com.rtsoju.mongle.presentation.util.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.InputStream
import javax.inject.Inject

@HiltViewModel
class KakaoExportViewModel @Inject constructor(
    private val uploadKakaotalk: UploadKakaotalkUseCase
) : BaseViewModel() {

    private val _isAnalyzing = MutableLiveData(false)
    val isAnalyzing = _isAnalyzing.asLiveData()

    fun uploadKakaotalkWithRoom(roomName: String, stream: InputStream) =
        viewModelScope.launch(Dispatchers.IO) {
            val response = startProgressTask {
                uploadKakaotalk(roomName, stream)
            }
            if (response != null) {
                _isAnalyzing.postValue(true)
            }
        }
}