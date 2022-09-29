package com.won983212.mongle.presentation.view.tutorial

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.won983212.mongle.R
import com.won983212.mongle.databinding.FragmentTutorialPageBinding
import com.won983212.mongle.presentation.util.dpToPxInt

class TutorialPageFragment : Fragment() {

    private var titleRes: Int? = null
    private var subtitleRes: Int? = null
    private var imageRes: Int? = null
    private var imageWidth: Float? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            titleRes = it.getInt(ARGUMENT_TITLE_RES)
            subtitleRes = it.getInt(ARGUMENT_SUBTITLE_RES)
            imageRes = it.getInt(ARGUMENT_IMAGE_RES)
            imageWidth = it.getFloat(ARGUMENT_IMAGE_WIDTH)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val context = requireContext()
        val view = FragmentTutorialPageBinding.inflate(inflater, container, false)
        view.apply {
            if (isValidRes(subtitleRes)) {
                textTutorialTitle.textSize = 20f
                textTutorialTitle.typeface = ResourcesCompat.getFont(context, R.font.app_bold)
                textTutorialSubtitle.text = context.getText(subtitleRes!!)
                textTutorialSubtitle.visibility = View.VISIBLE
            }
            if (isValidRes(titleRes) && isValidRes(imageRes)) {
                textTutorialTitle.text = context.getText(titleRes!!)
                imageTutorial.setImageResource(imageRes!!)
            }
            if (imageWidth != null && imageWidth != 0f) {
                imageTutorial.layoutParams.width = dpToPxInt(context, imageWidth!!.toInt())
                imageTutorial.requestLayout()
            }
        }
        return view.root
    }

    private fun isValidRes(res: Int?): Boolean {
        return res != null && res != 0
    }

    companion object {
        private const val ARGUMENT_TITLE_RES = "titleRes"
        private const val ARGUMENT_SUBTITLE_RES = "subtitleRes"
        private const val ARGUMENT_IMAGE_RES = "imageRes"
        private const val ARGUMENT_IMAGE_WIDTH = "imageWidth"

        fun newInstance(
            @StringRes titleRes: Int,
            @StringRes subtitleRes: Int,
            @DrawableRes imageRes: Int,
            imageWidth: Float
        ) =
            TutorialPageFragment().apply {
                arguments = bundleOf(
                    ARGUMENT_TITLE_RES to titleRes,
                    ARGUMENT_SUBTITLE_RES to subtitleRes,
                    ARGUMENT_IMAGE_RES to imageRes,
                    ARGUMENT_IMAGE_WIDTH to imageWidth
                )
            }
    }
}