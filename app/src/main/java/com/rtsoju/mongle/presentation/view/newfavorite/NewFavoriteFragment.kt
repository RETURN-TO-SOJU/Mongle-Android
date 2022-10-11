package com.rtsoju.mongle.presentation.view.newfavorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.rtsoju.mongle.R
import com.rtsoju.mongle.databinding.BottomSheetNewFavoriteBinding
import com.rtsoju.mongle.domain.model.Emotion
import com.rtsoju.mongle.presentation.util.getSerializableCompat
import com.rtsoju.mongle.presentation.util.toastLong

class NewFavoriteFragment : BottomSheetDialogFragment() {

    private val viewModel by viewModels<NewFavoriteViewModel>()
    private lateinit var binding: BottomSheetNewFavoriteBinding
    private var requestNewFavorite: OnRequestNewFavorite? = null
    private var initialEmotion: Emotion? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            initialEmotion = it.getSerializableCompat(ARGUMENT_INITIAL_EMOTION)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = BottomSheetNewFavoriteBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.btnNewFavoriteOk.setOnClickListener {
            if (!viewModel.saveFavorite()) {
                requireContext().toastLong(R.string.new_favorite_fill_blanks)
            } else {
                dismiss()
            }
        }

        binding.selectorNewFavoriteEmotion.setOnSelectedListener {
            viewModel.selectEmotion(it)
        }

        if (initialEmotion != null) {
            viewModel.selectEmotion(initialEmotion!!)
        }

        viewModel.eventNewFavorite.observe(this) {
            requestNewFavorite?.onNewFavorite(it.first, it.second)
        }

        return binding.root
    }

    fun setOnRequestNewFavorite(listener: OnRequestNewFavorite) {
        requestNewFavorite = listener
    }


    fun interface OnRequestNewFavorite {
        fun onNewFavorite(title: String, emotion: Emotion)
    }

    companion object {
        private const val ARGUMENT_INITIAL_EMOTION = "initialEmotion"

        fun newInstance(initialEmotion: Emotion) =
            NewFavoriteFragment().apply {
                arguments = bundleOf(ARGUMENT_INITIAL_EMOTION to initialEmotion)
            }
    }
}