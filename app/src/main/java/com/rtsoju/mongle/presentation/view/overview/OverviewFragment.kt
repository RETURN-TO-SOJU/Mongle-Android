package com.rtsoju.mongle.presentation.view.overview

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.rtsoju.mongle.databinding.FragmentOverviewBinding
import com.rtsoju.mongle.domain.model.Emotion
import com.rtsoju.mongle.presentation.util.getSerializableExtraCompat
import com.rtsoju.mongle.presentation.view.daydetail.DayDetailActivity
import com.rtsoju.mongle.presentation.view.tutorial.TutorialActivity
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate

@AndroidEntryPoint
class OverviewFragment : Fragment() {

    private val viewModel by viewModels<OverviewViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentOverviewBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val today = LocalDate.now()

        viewModel.apply {
            eventCalendarDataLoaded.observe(viewLifecycleOwner) {
                binding.calendarOverview.addDayEmotions(it)
            }
        }

        binding.calendarOverview.apply {
            setOnSelectedListener { date ->
                viewModel.setSelectedDate(date)
            }
            setOnInitializedListener {
                selectDate(today)
            }
            setOnMonthLoadedListener { from, to ->
                viewModel.loadCalendarData(from, to)
            }
        }

        binding.btnOverviewTutorialKakaoExport.setOnClickListener {
            context?.let { it1 -> TutorialActivity.startKakaoTutorial(it1) }
        }

        val openDetail =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                val selected = it?.data?.getSerializableExtraCompat(
                    DayDetailActivity.RESULT_SELECTED_DATE
                ) ?: binding.calendarOverview.selectedDate

                if (selected != null) {
                    viewModel.setSelectedDate(selected)
                    binding.calendarOverview.selectDate(selected)

                    val changedEmotion: Emotion? =
                        it?.data?.getSerializableExtraCompat(DayDetailActivity.RESULT_CHANGED_EMOTION)

                    if (changedEmotion != null) {
                        viewModel.updateEmotion(selected, changedEmotion)
                        binding.calendarOverview.addDayEmotions(
                            mapOf(selected to changedEmotion)
                        )
                    }
                }
            }

        binding.layoutOverviewSummaryCard.setOnClickListener {
            val date = binding.calendarOverview.selectedDate
            if (date != null) {
                Intent(context, DayDetailActivity::class.java).apply {
                    putExtra(DayDetailActivity.EXTRA_DATE, date)
                    openDetail.launch(this)
                }
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
}