package com.won983212.mongle.presentation.view.tutorial

import android.content.Context
import android.content.res.Resources
import androidx.annotation.ArrayRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

internal class TutorialSlideAdapter(
    fa: FragmentActivity,
    @ArrayRes titleListRes: Int,
    @ArrayRes subtitleListRes: Int,
    @ArrayRes imageListRes: Int,
    private val imageWidth: Float
) : FragmentStateAdapter(fa) {

    val size: Int
    private val titleList: IntArray?
    private val subtitleList: IntArray?
    private val imageResList: IntArray?

    init {
        titleList = getOrElse(fa, titleListRes)
        subtitleList = getOrElse(fa, subtitleListRes)
        imageResList = getOrElse(fa, imageListRes)
        size = titleList?.size ?: 0
    }

    private fun getOrElse(context: Context, @ArrayRes res: Int): IntArray? {
        return try {
            val typedArray = context.resources.obtainTypedArray(res)
            val len = typedArray.length()
            val result = IntArray(len)

            for (idx in 0 until typedArray.length()) {
                result[idx] = typedArray.getResourceId(idx, 0)
            }

            typedArray.recycle()
            result
        } catch (e: Resources.NotFoundException) {
            null
        }
    }

    override fun getItemCount(): Int = size

    override fun createFragment(position: Int): Fragment {
        val titleRes = titleList?.get(position) ?: 0
        val subtitleRes = subtitleList?.get(position) ?: 0
        val imageRes = imageResList?.get(position) ?: 0
        return TutorialPageFragment.newInstance(titleRes, subtitleRes, imageRes, imageWidth)
    }
}