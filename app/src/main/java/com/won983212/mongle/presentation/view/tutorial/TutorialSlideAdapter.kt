package com.won983212.mongle.presentation.view.tutorial

import android.content.res.TypedArray
import androidx.annotation.ArrayRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

internal class TutorialSlideAdapter(
    fa: FragmentActivity,
    @ArrayRes val titleListRes: Int,
    @ArrayRes val imageListRes: Int
) : FragmentStateAdapter(fa) {

    val size: Int
    private val titleList: TypedArray
    private val imageResList: TypedArray

    init {
        titleList = fa.resources.obtainTypedArray(titleListRes)
        imageResList = fa.resources.obtainTypedArray(imageListRes)
        size = titleList.length()
    }

    override fun getItemCount(): Int = size

    override fun createFragment(position: Int): Fragment {
        val titleRes = titleList.getResourceId(position, 0)
        val imageRes = imageResList.getResourceId(position, 0)
        return TutorialPageFragment.newInstance(titleRes, imageRes)
    }
}