package com.won983212.mongle

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.won983212.mongle.databinding.ActivityTestBinding
import com.won983212.mongle.password.PasswordActivity
import com.won983212.mongle.tutorial.TutorialActivity

data class ScreenInfo(
    val cls: Class<out Any>,
    val data: Bundle? = null
)

class TestActivity : AppCompatActivity() {
    private val listItems = arrayOf(
        "로그인" to ScreenInfo(LoginActivity::class.java),
        "이용 약관" to ScreenInfo(AgreeActivity::class.java),
        "비밀번호 입력" to ScreenInfo(PasswordActivity::class.java),
        "계정 연동" to ScreenInfo(IntegrationFragment::class.java),
        "튜토리얼" to ScreenInfo(TutorialActivity::class.java, makeTutorialBundle()),
        "카톡 튜토리얼" to ScreenInfo(TutorialActivity::class.java, makeKakaoTutorialBundle()),
        "찜 추가" to ScreenInfo(NewFavoriteFragment::class.java),
        "카카오 카톡 데이터 전송" to ScreenInfo(KakaoReceiveActivity::class.java),
        "분석된 캘린더 화면" to ScreenInfo(OverviewActivity::class.java)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        val binding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val items = listItems.map { it.first }.toTypedArray()
        binding.listview.let {
            it.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
            it.setOnItemClickListener { parent, view, position, id ->
                val screen = listItems[position].second
                if (Activity::class.java.isAssignableFrom(screen.cls)) {
                    val intent = Intent(this, screen.cls)
                    if (screen.data != null) {
                        intent.putExtras(screen.data)
                    }
                    startActivity(intent)
                } else if (BottomSheetDialogFragment::class.java.isAssignableFrom(screen.cls)) {
                    val bottomSheet = screen.cls.newInstance() as BottomSheetDialogFragment
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
}