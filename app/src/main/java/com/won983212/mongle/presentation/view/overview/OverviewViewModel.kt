package com.won983212.mongle.presentation.view.overview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.won983212.mongle.R
import com.won983212.mongle.common.util.asLiveData
import com.won983212.mongle.data.model.Emotion
import com.won983212.mongle.domain.repository.UserRepository
import com.won983212.mongle.presentation.base.BaseViewModel
import com.won983212.mongle.presentation.util.TextResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class OverviewViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {

    private val _keywords = MutableLiveData<List<Keyword>>(listOf())
    val keywords = Transformations.map(_keywords) { value ->
        value.ifEmpty {
            listOf(Keyword("비어있음"))
        }
    }

    private val _calendarEmotions = MutableLiveData(mapOf<LocalDate, Emotion>())
    val calendarEmotions = _calendarEmotions.asLiveData()

    private val _overviewText = MutableLiveData(TextResource())
    val overviewText = _overviewText.asLiveData()

    private val _selectedDayEmotion = MutableLiveData(R.drawable.emotion_anxious)
    val selectedDayEmotion = _selectedDayEmotion.asLiveData()

    private val _selectedDayTitle = MutableLiveData(R.string.detail_diary_empty)
    val selectedDayTitle = _selectedDayTitle.asLiveData()

    val hasData = Transformations.map(_selectedDayTitle) {
        it != R.string.overview_title_empty
    }


    private fun getOverviewText(date: LocalDate): TextResource {
        if (date == LocalDate.now()) {
            return TextResource(R.string.overview_intro_message_today)
        }

        val emotion = calendarEmotions.value?.get(date)
        val resId = emotion?.descriptionRes
            ?: R.string.overview_intro_message_other_day
        return TextResource(resId)
    }

    private suspend fun updateUserInfo(date: LocalDate) {
        val userInfo = userRepository.getUserInfo(this@OverviewViewModel)
        if (userInfo != null) {
            val dateText = date.format(DateTimeFormatter.ofPattern("M월 d일 EEEE"))
            _overviewText.value = TextResource(
                R.string.overview_intro,
                userInfo.name,
                dateText,
                getOverviewText(date)
            )
        }
    }

    fun onSelectionChanged(date: LocalDate) = viewModelScope.launch {
        updateUserInfo(date)
        val emotion = calendarEmotions.value?.get(date)
        _selectedDayEmotion.postValue((emotion ?: Emotion.ANXIOUS).iconRes)
        _selectedDayTitle.postValue(emotion?.descriptionRes ?: R.string.overview_title_empty)
    }
}