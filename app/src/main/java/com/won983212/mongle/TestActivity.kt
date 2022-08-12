package com.won983212.mongle

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.won983212.mongle.base.Emotion
import com.won983212.mongle.databinding.ActivityTestBinding
import com.won983212.mongle.view.*
import com.won983212.mongle.view.password.PasswordActivity
import com.won983212.mongle.view.tutorial.TutorialActivity

interface IScreenInfo {
    val name: String
}

data class FragmentInfo(
    override val name: String,
    val factory: () -> BottomSheetDialogFragment
) : IScreenInfo

data class ActivityInfo(
    override val name: String,
    val cls: Class<out Any>,
    val data: Bundle? = null
) : IScreenInfo

class TestActivity : AppCompatActivity() {
    private val listItems: Array<IScreenInfo> = arrayOf(
        ActivityInfo("로그인", LoginActivity::class.java),
        ActivityInfo("이용 약관", AgreeActivity::class.java),
        ActivityInfo("비밀번호 입력", PasswordActivity::class.java),
        FragmentInfo("계정 연동", this::integrationFragmentFactory),
        ActivityInfo("튜토리얼", TutorialActivity::class.java, makeTutorialBundle()),
        ActivityInfo("카톡 튜토리얼", TutorialActivity::class.java, makeKakaoTutorialBundle()),
        FragmentInfo("찜 추가", this::newFavoriteFragmentFactory),
        ActivityInfo("카카오 카톡 데이터 전송", KakaoReceiveActivity::class.java),
        ActivityInfo("분석된 캘린더 화면", OverviewActivity::class.java),
        ActivityInfo("분석된 캘린더 상세 화면", DayDetailActivity::class.java)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        val binding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val items = listItems.map { it.name }.toTypedArray()
        binding.listTests.let {
            it.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
            it.setOnItemClickListener { _, _, position, _ ->
                val screen = listItems[position]
                if (screen is ActivityInfo) {
                    val intent = Intent(this, screen.cls)
                    if (screen.data != null) {
                        intent.putExtras(screen.data)
                    }
                    startActivity(intent)
                } else if (screen is FragmentInfo) {
                    val bottomSheet = screen.factory()
                    bottomSheet.show(supportFragmentManager, bottomSheet.tag)
                } else {
                    Log.e("OnItemClickListener", "Unknown class type: $screen.cls")
                }
            }
        }
    }

    private fun makeTutorialBundle(): Bundle {
        return bundleOf(
            TutorialActivity.TITLE_LIST_RES to R.array.kakao_tutorial_title,
            TutorialActivity.IMAGE_LIST_RES to R.array.kakao_tutorial_image
        )
    }

    private fun makeKakaoTutorialBundle(): Bundle {
        return bundleOf(
            TutorialActivity.TITLE_LIST_RES to R.array.kakao_tutorial_title,
            TutorialActivity.IMAGE_LIST_RES to R.array.kakao_tutorial_image
        )
    }

    private fun integrationFragmentFactory(): BottomSheetDialogFragment {
        return IntegrationFragment()
    }

    private fun newFavoriteFragmentFactory(): BottomSheetDialogFragment {
        return NewFavoriteFragment(Emotion.ANXIOUS)
    }
}