package com.won983212.mongle.presentation.view.overview

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.won983212.mongle.databinding.FragmentOverviewBinding
import com.won983212.mongle.presentation.view.daydetail.DayDetailActivity
import com.won983212.mongle.presentation.view.tutorial.TutorialActivity
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
            attachDefaultHandlers(requireActivity())
            synchronize()
            calendarEmotions.observe(viewLifecycleOwner) {
                binding.calendarOverview.setDayEmotions(it)
            }
        }

        binding.calendarOverview.apply {
            setOnSelectionChangedListener { date ->
                viewModel.onSelectionChanged(date)
            }
            setOnInitializedListener {
                selectDate(today)
            }
        }

        binding.btnOverviewTutorialKakaoExport.setOnClickListener {
            context?.let { it1 -> TutorialActivity.startKakaoTutorial(it1) }
        }

        // TODO For test. 중간발표 이후 refactoring
        val openDetail =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                val selectedDate = binding.calendarOverview.selectedDate
                if (selectedDate != null) {
                    viewModel.onSelectionChanged(selectedDate)
                }
            }

        binding.btnOverviewShowDetail.setOnClickListener {
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