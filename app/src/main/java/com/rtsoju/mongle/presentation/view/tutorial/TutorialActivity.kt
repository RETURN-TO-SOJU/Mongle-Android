package com.rtsoju.mongle.presentation.view.tutorial

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.annotation.ArrayRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.rtsoju.mongle.R
import com.rtsoju.mongle.databinding.ActivityTutorialBinding
import com.rtsoju.mongle.presentation.view.tutorial.TutorialActivity.Companion.EXTRA_IMAGE_LIST_RES
import com.rtsoju.mongle.presentation.view.tutorial.TutorialActivity.Companion.EXTRA_IMAGE_WIDTH
import com.rtsoju.mongle.presentation.view.tutorial.TutorialActivity.Companion.EXTRA_SUBTITLE_LIST_RES
import com.rtsoju.mongle.presentation.view.tutorial.TutorialActivity.Companion.EXTRA_TITLE_LIST_RES

/**
 * ## Extras
 * * **(필수)** [EXTRA_TITLE_LIST_RES]: [ArrayRes] -
 * 튜토리얼 메시지(String)들의 id 배열
 *
 * * **(선택)** [EXTRA_SUBTITLE_LIST_RES]: [ArrayRes] -
 * 튜토리얼 부제목(String) id 배열. 설정하면 제목이 커지고, 아래 부제목이 표시된다.
 *
 * * **(필수)** [EXTRA_IMAGE_LIST_RES]: [ArrayRes] -
 * 튜토리얼 사진(Drawable)들의 id 배열
 *
 * * **(선택)** [EXTRA_IMAGE_WIDTH]: [Float] -
 * 튜토리얼 사진의 너비를 직접 지정한다. 지정하지 않으면 화면에 꽉차도록 자동 설정된다.
 * 단위는 dp이고, float으로 지정해야한다.
 *
 * 반드시 튜토리얼 메시지, 사진 id 배열은 길이가 같아야 한다.
 */
class TutorialActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityTutorialBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val titleResList = intent.getIntExtra(EXTRA_TITLE_LIST_RES, 0)
        val subtitleResList = intent.getIntExtra(EXTRA_SUBTITLE_LIST_RES, 0)
        val imageResList = intent.getIntExtra(EXTRA_IMAGE_LIST_RES, 0)
        val imageWidth = intent.getFloatExtra(EXTRA_IMAGE_WIDTH, 0f)

        checkIntentExtras(titleResList, subtitleResList, imageResList)

        binding.pagerTutorial.let {
            // titleResList, imageResList는 위에서 null체크를 하므로 cast error가 발생할 수 없음.
            val adapter = TutorialSlideAdapter(
                this, titleResList, subtitleResList,
                imageResList, imageWidth
            )
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
        subtitleResList: Int,
        imageResList: Int,
    ) {
        if (titleResList == 0) {
            throw IllegalArgumentException("titleResList is not provided.")
        }

        val titleListSize = resources.getStringArray(titleResList).size

        if (subtitleResList != 0) {
            val subtitleListSize = resources.getIntArray(subtitleResList).size
            if (subtitleListSize != titleListSize) {
                throw IllegalArgumentException("subtitleResList.size is not matched to titleListSize.size. (subtitleResList.size: ${subtitleListSize}, titleListSize: $titleListSize")
            }
        }

        if (imageResList == 0) {
            throw IllegalArgumentException("imageResList is not provided.")
        }

        val imageListSize = resources.getIntArray(imageResList).size
        if (imageListSize != titleListSize) {
            throw IllegalArgumentException("imageResList.size is not matched to titleListSize.size. (imageResList.size: ${imageListSize}, titleListSize: $titleListSize")
        }
    }

    companion object {
        const val EXTRA_TITLE_LIST_RES = "titleListRes"
        const val EXTRA_SUBTITLE_LIST_RES = "subtitleListRes"
        const val EXTRA_IMAGE_LIST_RES = "imageListRes"
        const val EXTRA_IMAGE_WIDTH = "imageWidth"

        fun startKakaoTutorial(context: Context): Intent {
            return Intent(context, TutorialActivity::class.java).apply {
                putExtra(EXTRA_TITLE_LIST_RES, R.array.kakao_tutorial_title)
                putExtra(EXTRA_IMAGE_LIST_RES, R.array.kakao_tutorial_image)
                context.startActivity(this)
            }
        }

        fun startSecurityTutorial(context: Context): Intent {
            return Intent(context, TutorialActivity::class.java).apply {
                putExtra(EXTRA_TITLE_LIST_RES, R.array.security_tutorial_title)
                putExtra(EXTRA_SUBTITLE_LIST_RES, R.array.security_tutorial_subtitle)
                putExtra(EXTRA_IMAGE_LIST_RES, R.array.security_tutorial_image)
                putExtra(EXTRA_IMAGE_WIDTH, 190f)
                context.startActivity(this)
            }
        }
    }
}