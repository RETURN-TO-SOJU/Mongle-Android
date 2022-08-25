package com.won983212.mongle.presentation.view.favorite

import android.content.res.TypedArray
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.won983212.mongle.R
import com.won983212.mongle.presentation.view.tutorial.TutorialPageFragment

internal class TutorialSlideAdapter(
    fa: Fragment
) : FragmentStateAdapter(fa) {

    val size: Int
    val titleList: TypedArray
    val imageResList: TypedArray

    init {
        titleList = fa.resources.obtainTypedArray(R.array.kakao_tutorial_title)
        imageResList = fa.resources.obtainTypedArray(R.array.kakao_tutorial_image)
        size = titleList.length()
    }

    override fun getItemCount(): Int = size

    override fun createFragment(position: Int): Fragment {
        val titleRes = titleList.getResourceId(position, 0)
        val imageRes = imageResList.getResourceId(position, 0)
        return TutorialPageFragment(titleRes, imageRes)
    }
}