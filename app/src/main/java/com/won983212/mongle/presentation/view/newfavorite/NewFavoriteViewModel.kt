package com.won983212.mongle.presentation.view.newfavorite

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.won983212.mongle.common.util.asLiveData
import com.won983212.mongle.data.model.Emotion
import com.won983212.mongle.presentation.base.BaseViewModel
import javax.inject.Inject

class NewFavoriteViewModel @Inject constructor(
) : BaseViewModel() {

    private val _selectedEmotion = MutableLiveData(Emotion.values()[0])
    val selectedEmotion = _selectedEmotion.asLiveData()

    val title = MutableLiveData("")


    fun selectEmotion(emotion: Emotion) {
        _selectedEmotion.postValue(emotion)
    }

    /**
     * 찜 등록. 사용자가 입력한 데이터를 바탕으로 등록한다.
     * @return 성공 여부. 실패시 false리턴. 실패 원인은 제목 미기입.
     */
    fun saveFavorite(): Boolean {
        if (title.value?.isNotBlank() != true) {
            return false
        }
        // TODO 찜 등록
        Log.d(
            "NewFavoriteViewModel",
            "Added favorite emotion: ${selectedEmotion.value}, title: ${title.value}"
        )
        return true
    }
}