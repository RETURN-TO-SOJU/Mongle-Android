package com.rtsoju.mongle.debug.view

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.rtsoju.mongle.data.source.remote.dto.MessageResult
import com.rtsoju.mongle.domain.repository.CalendarRepository
import com.rtsoju.mongle.presentation.base.BaseViewModel
import com.rtsoju.mongle.presentation.util.toastShort
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
        ManualInfo("UPDATE 일기", this::updateDiary)
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.attachDefaultHandlers(this)
    }

    private fun updateDiary() = lifecycleScope.launch {
        val result = viewModel.startProgressTask {
            calendarRepository.updateDiary(
                LocalDate.of(2021, 12, 11),
                "안녕하세요. 이 일기를 작성한 시각은 " + LocalDateTime.now()
                    .format(DateTimeFormatter.ISO_DATE) + "입니다.",
                ""
            )
        }
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