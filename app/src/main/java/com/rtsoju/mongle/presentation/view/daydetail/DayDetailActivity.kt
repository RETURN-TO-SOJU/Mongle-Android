package com.rtsoju.mongle.presentation.view.daydetail

import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.rtsoju.mongle.R
import com.rtsoju.mongle.databinding.ActivityDayDetailBinding
import com.rtsoju.mongle.domain.model.CachePolicy
import com.rtsoju.mongle.domain.model.Emotion
import com.rtsoju.mongle.presentation.base.BaseDataActivity
import com.rtsoju.mongle.presentation.base.event.OnSelectedListener
import com.rtsoju.mongle.presentation.view.daydetail.DayDetailActivity.Companion.EXTRA_DATE
import com.rtsoju.mongle.presentation.view.daydetail.DayDetailActivity.Companion.EXTRA_SHOW_ARRIVED_GIFT_DIALOG
import com.rtsoju.mongle.presentation.view.daydetail.DayDetailActivity.Companion.RESULT_SELECTED_DATE
import com.rtsoju.mongle.presentation.view.daydetail.adapter.AnalyzedEmotionListAdapter
import com.rtsoju.mongle.presentation.view.daydetail.adapter.PhotoListAdapter
import com.rtsoju.mongle.presentation.view.dialog.GiftArrivedDialog
import com.rtsoju.mongle.presentation.view.diary.EditDiaryActivity
import com.rtsoju.mongle.presentation.view.messages.EmotionMessagesActivity
import com.rtsoju.mongle.presentation.view.newfavorite.NewFavoriteFragment
import com.rtsoju.mongle.presentation.view.setemotion.SetEmotionFragment
import com.rtsoju.mongle.util.DatetimeFormats
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.util.*

/**
 * ## Extras
 * * **(필수)** [EXTRA_DATE]: [LocalDate] -
 * 어떤 날을 상세보기로 볼 것인지 정한다.
 * * **(선택)** [EXTRA_SHOW_ARRIVED_GIFT_DIALOG]: [Boolean] -
 * 선물 도착 Dialog를 띄운다. 기본값은 false
 *
 * ## Result
 * * [RESULT_SELECTED_DATE]: [LocalDate] -
 * 이 activity내에서 선택된 date를 반환한다.
 * 사용자가 선택하지 않았다면 [EXTRA_DATE]로 넘어온 date가 반환된다.
 * * [RESULT_CHANGED_EMOTION]: [Emotion] -
 * 이 activity내에서 Emotion을 변경하였다면 그 Emotion을 반환한다.
 * 바꾸지 않았다면 null
 */
@AndroidEntryPoint
class DayDetailActivity : BaseDataActivity<ActivityDayDetailBinding>(),
    OnSelectedListener<LocalDate> {

    private val viewModel by viewModels<DayDetailViewModel>()

    override val layoutId: Int = R.layout.activity_day_detail

    override fun onInitialize() {
        binding.viewModel = viewModel
        setSupportActionBar(binding.toolbarDayDetail)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        viewModel.eventOpenGiftDialog.observe(this) {
            GiftArrivedDialog(this, it.format(DatetimeFormats.DATE_DOT)).open()
        }

        viewModel.attachDefaultHandlers(this)
        viewModel.initializeByIntent(intent)
        saveResult(viewModel.date, null)

        initializeUI()
        LocalDateImageStore(this).readMediaStoreImages(viewModel.date) {
            if (it != null) {
                viewModel.setLocalPhoto(it)
            }
        }
    }

    private fun initializeUI() {
        val openDiary =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                viewModel.refresh(CachePolicy.REFRESH)
            }

        binding.btnDayDetailChangeEmotion.setOnClickListener {
            val fragment = SetEmotionFragment.newInstance(viewModel.date, viewModel.emotion)
            fragment.setOnSelectedListener {
                saveResult(viewModel.date, it)
                viewModel.setEmotion(it)
            }
            fragment.show(supportFragmentManager, fragment.tag)
        }

        binding.btnDayDetailBack.setOnClickListener {
            finish()
        }

        binding.btnDayDetailFavorite.setOnClickListener {
            val fragment = NewFavoriteFragment.newInstance(viewModel.emotion ?: Emotion.values()[0])
            fragment.setOnRequestNewFavorite { title, emotion ->
                viewModel.addFavorite(title, emotion)
            }
            fragment.show(supportFragmentManager, fragment.tag)
        }

        binding.textDayDetailDiary.setOnClickListener {
            Intent(this, EditDiaryActivity::class.java).apply {
                val emotion = viewModel.emotion
                if (emotion != null) {
                    putExtra(EditDiaryActivity.EXTRA_EMOTION_RES, emotion.iconRes)
                }
                putExtra(EditDiaryActivity.EXTRA_DATE, viewModel.date)
                putExtra(EditDiaryActivity.EXTRA_INITIAL_DIARY, viewModel.diary.value)
                openDiary.launch(this)
            }
        }

        binding.listDayDetailAnalyzedEmotion.adapter = AnalyzedEmotionListAdapter {
            openEmotionMessagesActivity(it)
        }

        binding.listDayDetailPhoto.adapter = PhotoListAdapter()

        val dayOfWeek = viewModel.date.dayOfWeek.value % 7
        binding.calendarDayDetailWeekday.run {
            startDay = viewModel.date.minusDays(dayOfWeek.toLong())
            selectIndex(dayOfWeek)
            setOnSelectionChangedListener(this@DayDetailActivity)
        }
    }

    override fun onSelected(value: LocalDate) {
        viewModel.setDate(value)
        saveResult(viewModel.date, null)
    }

    private fun saveResult(date: LocalDate?, emotion: Emotion?) {
        setResult(
            RESULT_OK, Intent()
                .putExtra(RESULT_SELECTED_DATE, date)
                .putExtra(RESULT_CHANGED_EMOTION, emotion)
        )
    }

    private fun openEmotionMessagesActivity(emotion: Emotion) {
        Intent(this, EmotionMessagesActivity::class.java).apply {
            putExtra(EmotionMessagesActivity.EXTRA_DATE, viewModel.date)
            putExtra(EmotionMessagesActivity.EXTRA_EMOTION, emotion)

            val proportionMap = viewModel.analyzedEmotions.value?.associate {
                it.emotion to it.proportion
            }

            if (proportionMap != null) {
                val enumMap = EnumMap(Emotion.values().associateWith { proportionMap[it] ?: 0 })
                putExtra(EmotionMessagesActivity.EXTRA_PROPORTIONS, enumMap)
            }

            startActivity(this)
        }
    }

    companion object {
        const val EXTRA_DATE = "date"
        const val EXTRA_SHOW_ARRIVED_GIFT_DIALOG = "gift"
        const val RESULT_SELECTED_DATE = "selectedDate"
        const val RESULT_CHANGED_EMOTION = "changedEmotion"
    }
}