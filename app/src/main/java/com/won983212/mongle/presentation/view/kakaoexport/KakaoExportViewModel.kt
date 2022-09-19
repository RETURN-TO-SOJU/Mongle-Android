package com.won983212.mongle.presentation.view.kakaoexport

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.won983212.mongle.presentation.util.asLiveData
import com.won983212.mongle.domain.repository.KakaotalkRepository
import com.won983212.mongle.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.InputStream
import javax.inject.Inject

@HiltViewModel
class KakaoExportViewModel @Inject constructor(
    private val kakaotalkRepository: KakaotalkRepository
) : BaseViewModel() {

    private val _isAnalyzing = MutableLiveData(false)
    val isAnalyzing = _isAnalyzing.asLiveData()

    fun uploadKakaotalk(stream: InputStream) = viewModelScope.launch(Dispatchers.IO) {
        val content = stream.readBytes()
        val response = startProgressTask { kakaotalkRepository.upload(content) }
        if (response != null) {
            _isAnalyzing.postValue(true)
        }
    }
}