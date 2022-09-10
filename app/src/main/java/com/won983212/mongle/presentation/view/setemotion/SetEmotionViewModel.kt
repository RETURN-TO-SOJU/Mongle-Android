package com.won983212.mongle.presentation.view.setemotion

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.won983212.mongle.common.util.asLiveData
import com.won983212.mongle.data.model.Emotion
import com.won983212.mongle.presentation.base.BaseViewModel
import javax.inject.Inject

class SetEmotionViewModel @Inject constructor(
) : BaseViewModel() {

    private val _selectedEmotion = MutableLiveData(Emotion.values()[0])
    val selectedEmotion = _selectedEmotion.asLiveData()


    fun selectEmotion(emotion: Emotion) {
        _selectedEmotion.postValue(emotion)
    }

    fun saveEmotion() {
        // TODO Emotion save selected
        Log.d(
            "SetEmotionViewModel",
            "Set emotion: ${selectedEmotion.value}"
        )
    }
}