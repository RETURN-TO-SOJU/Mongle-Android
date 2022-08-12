package com.won983212.mongle.view.tutorial

import android.content.res.TypedArray
import android.os.Bundle
import androidx.annotation.ArrayRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.won983212.mongle.databinding.ActivityTutorialBinding

class TutorialActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityTutorialBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val titleResList = intent.getIntExtra(TITLE_LIST_RES, 0)
        val imageResList = intent.getIntExtra(IMAGE_LIST_RES, 0)

        checkIntentExtras(titleResList, imageResList)

        binding.pagerTutorial.let {
            // titleResList, imageResList는 위에서 null체크를 하므로 cast error가 발생할 수 없음.
            it.adapter = TutorialSlideAdapter(this, titleResList, imageResList)
            binding.indicatorPagerPage.attachTo(it)
        }
    }

    private fun checkIntentExtras(
        titleResList: Int,
        imageResList: Int,
    ) {
        if (titleResList == 0) {
            throw IllegalArgumentException("titleResList is not provided.")
        }

        val titleListSize = resources.getStringArray(titleResList).size

        if (imageResList == 0) {
            throw IllegalArgumentException("imageResList is not provided.")
        }

        val imageListSize = resources.getIntArray(imageResList).size
        if (imageListSize != titleListSize) {
            throw IllegalArgumentException("imageResList.size is not matched to titleListSize.size. (imageResList.size: ${imageListSize}, titleListSize: ${titleListSize}")
        }
    }

    private class TutorialSlideAdapter(
        fa: FragmentActivity,
        @ArrayRes val titleListRes: Int,
        @ArrayRes val imageListRes: Int
    ) : FragmentStateAdapter(fa) {

        val size: Int
        val titleList: TypedArray
        val imageResList: TypedArray

        init {
            titleList = fa.resources.obtainTypedArray(titleListRes)
            imageResList = fa.resources.obtainTypedArray(imageListRes)
            size = titleList.length()
        }

        override fun getItemCount(): Int = size

        override fun createFragment(position: Int): Fragment {
            val titleRes = titleList.getResourceId(position, 0)
            val imageRes = imageResList.getResourceId(position, 0)
            return TutorialPageFragment(titleRes, imageRes)
        }
    }

    companion object {
        const val TITLE_LIST_RES = "titleListRes"
        const val IMAGE_LIST_RES = "imageListRes"
    }
}