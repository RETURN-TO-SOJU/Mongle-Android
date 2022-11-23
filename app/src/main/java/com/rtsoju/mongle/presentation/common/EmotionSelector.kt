package com.rtsoju.mongle.presentation.common

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.GridLayout
import android.widget.ImageButton
import android.widget.ImageView
import com.rtsoju.mongle.databinding.ControlEmotionSelectorBinding
import com.rtsoju.mongle.domain.model.Emotion
import com.rtsoju.mongle.presentation.base.event.OnSelectedListener
import com.rtsoju.mongle.presentation.util.dpToPxInt

class EmotionSelector @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val binding =
        ControlEmotionSelectorBinding.inflate(LayoutInflater.from(context), this, false)

    private var onSelectedListener: OnSelectedListener<Emotion>? = null


    init {
        createEmotionButtons()
        addView(binding.root)
    }


    fun setOnSelectedListener(listener: OnSelectedListener<Emotion>) {
        onSelectedListener = listener
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
                    onSelectedListener?.onSelected(emotion)
                }
            }

            val size = dpToPxInt(context, 48)
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

            binding.root.addView(
                btn,
                layoutParams
            )
        }
    }
}