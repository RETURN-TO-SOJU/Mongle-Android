package com.won983212.mongle.view.tutorial

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
            binding.indicatorTutorialPagerPage.attachTo(it)
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

    companion object {
        const val TITLE_LIST_RES = "titleListRes"
        const val IMAGE_LIST_RES = "imageListRes"
    }
}