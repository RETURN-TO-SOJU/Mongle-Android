package com.won983212.mongle.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.won983212.mongle.databinding.BottomSheetLeavingBinding

class LeavingFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        val binding = BottomSheetLeavingBinding.inflate(inflater, container, false)
        binding.btnLeavingOk.setOnClickListener {
            setFragmentResult(REQUEST_KEY, bundleOf(RESULT_AGREED to true))
            dismiss()
        }
        
        return binding.root
    }

    companion object {
        const val REQUEST_KEY = "leaving"
        const val RESULT_AGREED = "agreed"

        fun newInstance() = LeavingFragment()
    }
}