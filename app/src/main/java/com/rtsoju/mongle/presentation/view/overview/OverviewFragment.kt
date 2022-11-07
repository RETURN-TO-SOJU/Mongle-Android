package com.rtsoju.mongle.presentation.view.overview

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.rtsoju.mongle.R
import com.rtsoju.mongle.databinding.FragmentOverviewBinding
import com.rtsoju.mongle.domain.model.Emotion
import com.rtsoju.mongle.presentation.common.calendar.MongleCalendar
import com.rtsoju.mongle.presentation.util.getSerializableExtraCompat
import com.rtsoju.mongle.presentation.view.daydetail.DayDetailActivity
import com.rtsoju.mongle.presentation.view.main.MainViewModel
import com.rtsoju.mongle.presentation.view.tutorial.TutorialActivity
import dagger.hilt.android.AndroidEntryPoint
import me.toptas.fancyshowcase.FancyShowCaseView
import me.toptas.fancyshowcase.FocusShape
import java.time.LocalDate

@AndroidEntryPoint
class OverviewFragment : Fragment() {

    private val viewModel by viewModels<OverviewViewModel>()
    private val mainViewModel by activityViewModels<MainViewModel>()
    private lateinit var dayDetailResult: ActivityResultLauncher<Intent>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentOverviewBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val activity = requireActivity()
        val context = requireContext()
        val today = LocalDate.now()

        dayDetailResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                callbackDayDetailResult(binding.calendarOverview, it?.data)
            }

        viewModel.attachDefaultErrorHandler(activity)
        viewModel.apply {
            eventCalendarDataLoaded.observe(viewLifecycleOwner) {
                binding.calendarOverview.addDayEmotions(it)
            }
        }

        mainViewModel.showShowcase.observe(viewLifecycleOwner) {
            if (it) {
                FancyShowCaseView.Builder(activity)
                    .focusOn(binding.btnOverviewTutorialKakaoExport)
                    .focusShape(FocusShape.ROUNDED_RECTANGLE)
                    .titleStyle(R.style.Widget_Mongle_ShowcaseTextView, Gravity.CENTER)
                    .title("카톡 분석방법을 알아볼까요?")
                    .build()
                    .show()
                mainViewModel.setShowShowcase(false)
            }
        }

        binding.calendarOverview.apply {
            setOnSelectedListener { date ->
                viewModel.setSelectedDate(date)
            }
            setOnClickSelectedListener { date ->
                openDayDetail(context, date)
            }
            setOnInitializedListener {
                selectDate(today)
            }
            setOnMonthLoadedListener { from, to ->
                viewModel.loadCalendarData(from, to)
            }
        }

        binding.btnOverviewTutorialKakaoExport.setOnClickListener {
            TutorialActivity.startKakaoTutorial(context)
        }

        binding.layoutOverviewSummaryCard.setOnClickListener {
            binding.calendarOverview.selectedDate?.let {
                openDayDetail(context, it)
            }
        }

        binding.listKeyword.apply {
            layoutManager = LinearLayoutManager(context).apply {
                orientation = LinearLayoutManager.HORIZONTAL
            }
            adapter = KeywordAdapter()
        }

        return binding.root
    }

    private fun openDayDetail(context: Context, date: LocalDate) {
        Intent(context, DayDetailActivity::class.java).apply {
            putExtra(DayDetailActivity.EXTRA_DATE, date)
            dayDetailResult.launch(this)
        }
    }

    /**
     * DayDetail에서 Emotion을 수정했으면, 이를 calendar와 local db에 반영해야 한다.
     * 이를 수행하는 callback
     */
    private fun callbackDayDetailResult(
        calendar: MongleCalendar,
        data: Intent?
    ) {
        val selected = data?.getSerializableExtraCompat(
            DayDetailActivity.RESULT_SELECTED_DATE
        ) ?: calendar.selectedDate

        if (selected != null) {
            viewModel.setSelectedDate(selected)
            calendar.selectDate(selected)

            val changedEmotion: Emotion? =
                data?.getSerializableExtraCompat(DayDetailActivity.RESULT_CHANGED_EMOTION)

            if (changedEmotion != null) {
                viewModel.updateEmotion(selected, changedEmotion)
                calendar.addDayEmotions(
                    mapOf(selected to changedEmotion)
                )
            }
        }
    }
}