package com.won983212.mongle.presentation.view.newfavorite

import androidx.lifecycle.MutableLiveData
import com.won983212.mongle.presentation.util.asLiveData
import com.won983212.mongle.domain.model.Emotion
import com.won983212.mongle.presentation.base.BaseViewModel
import com.won983212.mongle.presentation.util.SingleLiveEvent
import javax.inject.Inject

class NewFavoriteViewModel @Inject constructor(
) : BaseViewModel() {

    private val _eventNewFavorite = SingleLiveEvent<Pair<String, Emotion>>()
    val eventNewFavorite = _eventNewFavorite.asLiveData()

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

        val title = title.value
        val selectedEmotion = selectedEmotion.value
        if (title != null && selectedEmotion != null) {
            _eventNewFavorite.postValue(Pair(title, selectedEmotion))
        }

        return true
    }
}