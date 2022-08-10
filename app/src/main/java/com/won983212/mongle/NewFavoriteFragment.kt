package com.won983212.mongle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.won983212.mongle.databinding.BottomSheetNewFavoriteBinding

class NewFavoriteFragment(
    val initialEmotion: Emotion
) : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        val binding = BottomSheetNewFavoriteBinding.inflate(inflater, container, false)

        binding.newFavoriteTitle.setCompoundDrawablesWithIntrinsicBounds(
            initialEmotion.iconRes,
            0,
            0,
            0
        )

        binding.emotionContainer.children.forEach {
            val iconRes = Emotion.of(it.tag.toString()).iconRes
            it.setOnClickListener {
                binding.newFavoriteTitle.setCompoundDrawablesWithIntrinsicBounds(
                    iconRes,
                    0,
                    0,
                    0
                )
            }
        }

        return binding.root
    }
}