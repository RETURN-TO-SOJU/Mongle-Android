package com.rtsoju.mongle.presentation.view.kakaoexport

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.messaging.FirebaseMessaging
import com.rtsoju.mongle.domain.usecase.kakaotalk.UploadKakaotalkUseCase
import com.rtsoju.mongle.domain.usecase.user.SetFCMTokenUseCase
import com.rtsoju.mongle.presentation.base.BaseViewModel
import com.rtsoju.mongle.presentation.util.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.io.InputStream
import javax.inject.Inject

@HiltViewModel
class KakaoExportViewModel @Inject constructor(
    private val uploadKakaotalk: UploadKakaotalkUseCase,
    private val setFCMToken: SetFCMTokenUseCase
) : BaseViewModel() {

    private val _isAnalyzing = MutableLiveData(false)
    val isAnalyzing = _isAnalyzing.asLiveData()

    fun uploadKakaotalkWithRoom(roomName: String, stream: InputStream) =
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val token = FirebaseMessaging.getInstance().token.await()
                setFCMToken(token)
                Log.d("KakaoExportViewModel", "Upload Token")

                val response = startProgressTask {
                    uploadKakaotalk(roomName, stream)
                }

                if (response != null) {
                    _isAnalyzing.postValue(true)
                }
            } catch (e: java.lang.Exception) {
                Log.e("KakaoExportViewModel", e.toString())
                showError("오류가 발생했습니다.")
            }
        }
}