package com.won983212.mongle.presentation.view.test

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.won983212.mongle.common.util.toastShort
import com.won983212.mongle.data.model.Emotion
import com.won983212.mongle.data.source.remote.model.MessageResult
import com.won983212.mongle.domain.repository.CalendarRepository
import com.won983212.mongle.presentation.base.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@AndroidEntryPoint
class ApiTestActivity : BaseTestActivity() {

    @Inject
    lateinit var calendarRepository: CalendarRepository

    private val gson = Gson()
    private val viewModel by viewModels<BaseViewModel>()

    override val listItems: Array<IScreenInfo> = arrayOf(
        ManualInfo("UPDATE 일기", this::updateDiary),
        ManualInfo("GET 캘린더 월별 데이터", this::getCalendarDayMetadata),
        ManualInfo("GET 캘린더 일자 Detail", this::getCalendarDayDetail),
        ManualInfo("GET 일자 감정 문장", this::getDayEmotionalSentences)
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.attachDefaultErrorHandler(this)
        viewModel.attachDefaultLoadingHandler(this)
    }

    private fun updateDiary() = lifecycleScope.launch {
        val result = calendarRepository.updateDiary(
            viewModel,
            LocalDate.of(2021, 12, 11),
            "안녕하세요. 이 일기를 작성한 시각은 " + LocalDateTime.now()
                .format(DateTimeFormatter.ISO_DATE) + "입니다."
        )
        if (result != null) {
            showResult(result)
        }
    }

    private fun getCalendarDayMetadata() = lifecycleScope.launch {
        val result = calendarRepository.getCalendarDayMetadata(
            viewModel,
            LocalDate.now().minusMonths(5),
            LocalDate.now().plusMonths(5)
        )
        if (result != null) {
            showResult(result)
        }
    }

    private fun getCalendarDayDetail() = lifecycleScope.launch {
        val result = calendarRepository.getCalendarDayDetail(
            viewModel,
            LocalDate.now()
        )
        if (result != null) {
            showResult(result)
        }
    }

    private fun getDayEmotionalSentences() = lifecycleScope.launch {
        val result = calendarRepository.getDayEmotionalSentences(
            viewModel,
            LocalDate.now(),
            Emotion.HAPPY
        )
        if (result != null) {
            showResult(result)
        }
    }

    private fun showResult(result: Any) {
        val resultTest = if (result is MessageResult) {
            result.message
        } else {
            gson.toJson(result)
        }
        Log.d("ApiTextActivity", resultTest)
        toastShort(resultTest)
    }
}