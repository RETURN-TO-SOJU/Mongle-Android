package com.won983212.mongle.presentation.view.tutorial

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.won983212.mongle.databinding.FragmentTutorialPageBinding

class TutorialPageFragment : Fragment() {

    private var titleRes: Int? = null
    private var imageRes: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            titleRes = it.getInt(ARGUMENT_TITLE_RES)
            imageRes = it.getInt(ARGUMENT_IMAGE_RES)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = FragmentTutorialPageBinding.inflate(inflater, container, false)
        view.apply {
            if (titleRes != null && imageRes != null) {
                textTutorialTitle.text = requireContext().getText(titleRes!!)
                imageTutorial.setImageResource(imageRes!!)
            }
        }
        return view.root
    }

    companion object {
        private const val ARGUMENT_TITLE_RES = "titleRes"
        private const val ARGUMENT_IMAGE_RES = "imageRes"

        fun newInstance(@StringRes titleRes: Int, @DrawableRes imageRes: Int) =
            TutorialPageFragment().apply {
                arguments = bundleOf(
                    ARGUMENT_TITLE_RES to titleRes,
                    ARGUMENT_IMAGE_RES to imageRes
                )
            }
    }
}