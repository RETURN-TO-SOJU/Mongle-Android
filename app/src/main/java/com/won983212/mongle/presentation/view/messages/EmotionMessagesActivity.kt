package com.won983212.mongle.presentation.view.messages

import androidx.activity.viewModels
import com.won983212.mongle.R
import com.won983212.mongle.data.model.Emotion
import com.won983212.mongle.databinding.ActivityEmotionMessageBinding
import com.won983212.mongle.presentation.base.BaseDataActivity
import com.won983212.mongle.presentation.view.daydetail.DayDetailActivity.Companion.EXTRA_DATE
import com.won983212.mongle.presentation.view.messages.EmotionMessagesActivity.Companion.EXTRA_DATE
import com.won983212.mongle.presentation.view.messages.EmotionMessagesActivity.Companion.EXTRA_EMOTION
import com.won983212.mongle.presentation.view.messages.EmotionMessagesActivity.Companion.EXTRA_PROPORTIONS
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate

/**
 * ## Extras
 * * **(필수)** [EXTRA_DATE]: [LocalDate] -
 * 어떤 날의 감정 메시지들을 볼 것인지 선택한다
 * * **(필수)** [EXTRA_PROPORTIONS]: [EnumMap<Emotion, Int>](EnumMap) -
 * 각 감정의 비율을 포함한 EnumMap. 반드시 모든 감정을 포함하고 있어야 함
 * * **(선택)** [EXTRA_EMOTION]: [Emotion] -
 * 처음에 선택된 감정. 기본값은 Emotion의 0번째 값
 */
@AndroidEntryPoint
class EmotionMessagesActivity : BaseDataActivity<ActivityEmotionMessageBinding>() {

    override val layoutId: Int = R.layout.activity_emotion_message
    private val viewModel by viewModels<EmotionMessagesViewModel>()

    override fun onInitialize() {
        binding.viewModel = viewModel

        binding.listEmotionMessage.adapter = EmotionMessageListAdapter()
        binding.btnEmotionMessageCancel.setOnClickListener { finish() }
        viewModel.attachDefaultHandlers(this)
        viewModel.initializeFromIntent(intent)
    }

    companion object {
        const val EXTRA_DATE = "date"
        const val EXTRA_PROPORTIONS = "proportions"
        const val EXTRA_EMOTION = "emotion"
    }
}