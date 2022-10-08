package com.won983212.mongle.presentation.view.setemotion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.won983212.mongle.databinding.BottomSheetSetEmotionBinding
import com.won983212.mongle.domain.model.Emotion
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate

@AndroidEntryPoint
class SetEmotionFragment : BottomSheetDialogFragment() {

    private val viewModel by viewModels<SetEmotionViewModel>()
    private lateinit var binding: BottomSheetSetEmotionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            viewModel.initializeFromBundle(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = BottomSheetSetEmotionBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.btnNewFavoriteOk.setOnClickListener {
            viewModel.saveEmotion()
            dismiss()
        }

        binding.selectorSetEmotion.setOnSelectedListener {
            viewModel.selectEmotion(it)
        }

        return binding.root
    }


    companion object {
        const val ARGUMENT_DATE = "date"
        const val ARGUMENT_INITIAL_EMOTION = "initialEmotion"

        fun newInstance(date: LocalDate, initialEmotion: Emotion?) =
            SetEmotionFragment().apply {
                arguments = bundleOf(
                    ARGUMENT_DATE to date,
                    ARGUMENT_INITIAL_EMOTION to initialEmotion
                )
            }
    }
}