package com.won983212.mongle.view.tutorial

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.won983212.mongle.R
import com.won983212.mongle.databinding.ActivityTutorialBinding
import com.won983212.mongle.view.tutorial.TutorialActivity.Companion.EXTRA_IMAGE_LIST_RES
import com.won983212.mongle.view.tutorial.TutorialActivity.Companion.EXTRA_TITLE_LIST_RES

/**
 * ## Extras
 * * **(필수)** [EXTRA_TITLE_LIST_RES]: [IntArray] -
 * 튜토리얼 메시지(String)들의 id 배열
 *
 * * **(필수)** [EXTRA_IMAGE_LIST_RES]: [IntArray] -
 * 튜토리얼 사진(Drawable)들의 id 배열
 *
 * 반드시 튜토리얼 메시지, 사진 id 배열은 길이가 같아야 한다.
 */
class TutorialActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityTutorialBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val titleResList = intent.getIntExtra(EXTRA_TITLE_LIST_RES, 0)
        val imageResList = intent.getIntExtra(EXTRA_IMAGE_LIST_RES, 0)

        checkIntentExtras(titleResList, imageResList)

        binding.pagerTutorial.let {
            // titleResList, imageResList는 위에서 null체크를 하므로 cast error가 발생할 수 없음.
            val adapter = TutorialSlideAdapter(this, titleResList, imageResList)
            it.adapter = adapter
            it.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    binding.btnTutorialSkip.setText(
                        if (position == adapter.itemCount - 1) {
                            R.string.ok
                        } else {
                            R.string.skip
                        }
                    )
                }
            })
            binding.indicatorTutorialPagerPage.attachTo(it)
        }
        binding.btnTutorialSkip.setOnClickListener {
            finish()
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
        const val EXTRA_TITLE_LIST_RES = "titleListRes"
        const val EXTRA_IMAGE_LIST_RES = "imageListRes"
    }
}