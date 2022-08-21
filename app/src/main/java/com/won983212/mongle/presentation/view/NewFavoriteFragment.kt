package com.won983212.mongle.presentation.view

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageButton
import android.widget.ImageView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.won983212.mongle.common.util.dpToPx
import com.won983212.mongle.data.model.Emotion
import com.won983212.mongle.databinding.BottomSheetNewFavoriteBinding

class NewFavoriteFragment(
    private val initialEmotion: Emotion
) : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetNewFavoriteBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = BottomSheetNewFavoriteBinding.inflate(inflater, container, false)

        binding.edittextNewFavoriteTitle.setCompoundDrawablesWithIntrinsicBounds(
            initialEmotion.iconRes,
            0,
            0,
            0
        )

        createEmotionButtons()
        return binding.root
    }

    private fun createEmotionButtons() {
        Emotion.values().forEachIndexed { idx, emotion ->
            val btn = ImageButton(context).apply {
                scaleType = ImageView.ScaleType.CENTER_CROP
                setImageResource(emotion.iconRes)

                val typedValue = TypedValue()
                context.theme.resolveAttribute(
                    android.R.attr.selectableItemBackgroundBorderless,
                    typedValue,
                    true
                )
                setBackgroundResource(typedValue.resourceId)

                setOnClickListener {
                    binding.edittextNewFavoriteTitle.setCompoundDrawablesWithIntrinsicBounds(
                        emotion.iconRes,
                        0,
                        0,
                        0
                    )
                }
            }

            val size = dpToPx(context, 48)
            val layoutParams = GridLayout.LayoutParams(ViewGroup.LayoutParams(size, size))

            when (idx) {
                0 -> {
                    layoutParams.rightMargin = size
                    layoutParams.bottomMargin = size / 2
                }
                1 -> {
                    layoutParams.rightMargin = size
                }
            }

            binding.layoutNewFavoriteEmotionContainer.addView(
                btn,
                layoutParams
            )
        }
    }
}