package com.won983212.mongle.view.kakaoexport

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.won983212.mongle.common.base.BaseViewModel
import com.won983212.mongle.common.util.asLiveData
import com.won983212.mongle.data.remote.datasource.KakaoSendDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.InputStream
import javax.inject.Inject

@HiltViewModel
class KakaoExportViewModel @Inject constructor(
    private val dataSource: KakaoSendDataSource
) : BaseViewModel() {

    private val _isAnalyzing = MutableLiveData(false)
    val isAnalyzing = _isAnalyzing.asLiveData()

    fun sendKakaoTalkData(stream: InputStream) {
        viewModelScope.launch {
            val content = stream.readBytes()
            val response = dataSource.sendKakaoTalkData(this@KakaoExportViewModel, content)
            if (response != null) {
                _isAnalyzing.postValue(true)
            }
        }
    }
}