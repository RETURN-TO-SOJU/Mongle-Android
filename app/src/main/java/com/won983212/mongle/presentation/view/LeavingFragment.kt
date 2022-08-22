package com.won983212.mongle.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.won983212.mongle.databinding.BottomSheetLeavingBinding

class LeavingFragment(
    private val onAgree: () -> Unit
) : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        val binding = BottomSheetLeavingBinding.inflate(inflater, container, false)
        binding.btnLeavingOk.setOnClickListener {
            onAgree()
            dismiss()
        }

        return binding.root
    }
}