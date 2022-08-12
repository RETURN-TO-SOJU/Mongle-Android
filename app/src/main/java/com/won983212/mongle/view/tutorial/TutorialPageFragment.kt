package com.won983212.mongle.view.tutorial

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.won983212.mongle.databinding.FragmentTutorialPageBinding

class TutorialPageFragment(
    @StringRes val titleRes: Int,
    @DrawableRes val imageRes: Int
) :
    Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = FragmentTutorialPageBinding.inflate(inflater, container, false)
        view.apply {
            textTutorialTitle.text = requireContext().getText(titleRes)
            imageTutorial.setImageResource(imageRes)
        }
        return view.root
    }
}