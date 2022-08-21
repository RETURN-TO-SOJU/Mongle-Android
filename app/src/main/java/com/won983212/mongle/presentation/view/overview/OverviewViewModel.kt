package com.won983212.mongle.presentation.view.overview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.won983212.mongle.R
import com.won983212.mongle.common.util.asLiveData
import com.won983212.mongle.data.model.Emotion
import com.won983212.mongle.domain.repository.UserRepository
import com.won983212.mongle.presentation.base.BaseViewModel
import com.won983212.mongle.presentation.util.StringResourceWithArg
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

    private val _hasData = MutableLiveData(false)
    val hasData = _hasData.asLiveData()

    private val _calendarEmotions = MutableLiveData(mapOf<LocalDate, Emotion>())
    val calendarEmotions = _calendarEmotions.asLiveData()

    private val _overviewText = MutableLiveData(StringResourceWithArg())
    val overviewText = _overviewText.asLiveData()


    private fun getOverviewText(date: LocalDate): StringResourceWithArg {
        if (date == LocalDate.now()) {
            return StringResourceWithArg(R.string.overview_intro_message_today)
        }

        val emotion = calendarEmotions.value?.get(date)
        return if (emotion != null) {
            val resId = when (emotion) {
                Emotion.ANGRY -> R.string.overview_intro_message_angry
                Emotion.ANXIOUS -> R.string.overview_intro_message_anxious
                Emotion.HAPPY -> R.string.overview_intro_message_happy
                Emotion.NEUTRAL -> R.string.overview_intro_message_neutral
                Emotion.TIRED -> R.string.overview_intro_message_tired
                Emotion.SAD -> R.string.overview_intro_message_sad
            }
            StringResourceWithArg(resId)
        } else {
            StringResourceWithArg(R.string.overview_intro_message_other_day)
        }
    }

    fun updateOverviewText(date: LocalDate) = viewModelScope.launch {
        val userInfo = userRepository.getUserInfo(this@OverviewViewModel)
        if (userInfo != null) {
            val dateText = date.format(DateTimeFormatter.ofPattern("M월 d일 EEEE"))
            _overviewText.value = StringResourceWithArg(
                R.string.overview_intro,
                userInfo.name,
                dateText,
                getOverviewText(date)
            )
        }
    }
}