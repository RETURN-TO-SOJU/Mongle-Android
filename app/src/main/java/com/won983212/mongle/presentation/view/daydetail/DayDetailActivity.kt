package com.won983212.mongle.presentation.view.daydetail

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.won983212.mongle.DatetimeFormats
import com.won983212.mongle.R
import com.won983212.mongle.common.util.toastLong
import com.won983212.mongle.data.model.Emotion
import com.won983212.mongle.databinding.ActivityDayDetailBinding
import com.won983212.mongle.presentation.base.BaseDataActivity
import com.won983212.mongle.presentation.component.calendar.OnSelectionChangedListener
import com.won983212.mongle.presentation.view.daydetail.DayDetailActivity.Companion.EXTRA_DATE
import com.won983212.mongle.presentation.view.daydetail.DayDetailActivity.Companion.EXTRA_SHOW_ARRIVED_GIFT_DIALOG
import com.won983212.mongle.presentation.view.daydetail.adapter.AnalyzedEmotionListAdapter
import com.won983212.mongle.presentation.view.daydetail.adapter.PhotoListAdapter
import com.won983212.mongle.presentation.view.diary.EditDiaryActivity
import com.won983212.mongle.presentation.view.messages.EmotionMessagesActivity
import com.won983212.mongle.presentation.view.openGiftArrivedDialog
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

/**
 * ## Extras
 * * **(필수)** [EXTRA_DATE]: [LocalDate] -
 * 어떤 날을 상세보기로 볼 것인지 정한다.
 * * **(선택)** [EXTRA_SHOW_ARRIVED_GIFT_DIALOG]: [Boolean] -
 * 선물 도착 Dialog를 띄운다. 기본값은 false
 */
@AndroidEntryPoint
class DayDetailActivity : BaseDataActivity<ActivityDayDetailBinding>(), OnSelectionChangedListener {

    private val viewModel by viewModels<DayDetailViewModel>()

    override val layoutId: Int = R.layout.activity_day_detail

    override fun onInitialize() {
        binding.viewModel = viewModel
        setSupportActionBar(binding.toolbarDayDetail)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        viewModel.eventOpenGiftDialog.observe(this) {
            openGiftArrivedDialog(this, it.format(DatetimeFormats.DATE_DOT))
        }

        viewModel.attachDefaultHandlers(this)
        viewModel.initializeByIntent(intent)

        initializeUI()
        readMediaStoreImages(viewModel.date)
    }

    private fun initializeUI() {
        val openDiary =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                viewModel.refresh()
            }

        binding.btnDayDetailBack.setOnClickListener {
            finish()
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

    override fun onSelectionChanged(selection: LocalDate) {
        viewModel.setDate(selection)
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

    private fun readMediaStoreImages(date: LocalDate) {
        val requestPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    readPermissionGrantedImages(date)
                } else {
                    toastLong("사진 권한을 부여하지 않아, 사진을 불러올 수 없습니다.")
                }
            }

        val checkPermission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )

        if (checkPermission == PackageManager.PERMISSION_GRANTED) {
            readPermissionGrantedImages(date)
        } else {
            requestPermissionLauncher.launch(
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }
    }

    private fun readPermissionGrantedImages(date: LocalDate) {
        val instant = date.atStartOfDay(ZoneId.systemDefault()).toInstant()
        val epoch = instant.epochSecond
        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DATE_ADDED
        )
        val selection = "${MediaStore.Images.Media.DATE_ADDED} >= ? and " +
                "${MediaStore.Images.Media.DATE_ADDED} <= ?"
        val selectionArgs = arrayOf(
            epoch.toString(),
            (epoch + 86400).toString()
        )
        val query = contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            null
        )
        query?.use { cursor ->
            viewModel.readPhotosFromCursor(cursor)
        }
    }

    companion object {
        const val EXTRA_DATE = "date"
        const val EXTRA_SHOW_ARRIVED_GIFT_DIALOG = "gift"
    }
}