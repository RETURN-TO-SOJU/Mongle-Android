package com.won983212.mongle

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.won983212.mongle.base.Emotion
import com.won983212.mongle.databinding.ActivityTestBinding
import com.won983212.mongle.util.attachCompatAnim
import com.won983212.mongle.view.*
import com.won983212.mongle.view.overview.OverviewActivity
import com.won983212.mongle.view.password.PasswordActivity
import com.won983212.mongle.view.password.PasswordActivityMode
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

data class ManualInfo(
    override val name: String,
    val task: () -> Unit
) : IScreenInfo

class TestActivity : AppCompatActivity() {
    private val listItems: Array<IScreenInfo> = arrayOf(
        ActivityInfo("로그인", LoginActivity::class.java),
        ActivityInfo("이용 약관", AgreeActivity::class.java),
        ActivityInfo("비밀번호 입력", PasswordActivity::class.java),
        ActivityInfo("비밀번호 설정", PasswordActivity::class.java, passwordSetBundle()),
        FragmentInfo("계정 연동", this::integrationFragmentFactory),
        ActivityInfo("튜토리얼", TutorialActivity::class.java, makeTutorialBundle()),
        ActivityInfo("카톡 튜토리얼", TutorialActivity::class.java, makeKakaoTutorialBundle()),
        FragmentInfo("찜 추가", this::newFavoriteFragmentFactory),
        ActivityInfo("카카오 카톡 데이터 전송", KakaoSendingActivity::class.java),
        ActivityInfo("분석된 캘린더", OverviewActivity::class.java),
        ActivityInfo("분석된 캘린더 상세", DayDetailActivity::class.java),
        ActivityInfo("일기 작성", EditDiaryActivity::class.java, testDiaryMockBundle()),
        ManualInfo("분석 완료 다이얼로그", this::openAnalyzeCompleteDialog)
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
                when (val screen = listItems[position]) {
                    is ActivityInfo -> {
                        val intent = Intent(this, screen.cls)
                        if (screen.data != null) {
                            intent.putExtras(screen.data)
                        }
                        startActivity(intent)
                    }
                    is FragmentInfo -> {
                        val bottomSheet = screen.factory()
                        bottomSheet.show(supportFragmentManager, bottomSheet.tag)
                    }
                    is ManualInfo -> {
                        screen.task()
                    }
                    else -> {
                        Log.e("OnItemClickListener", "Unknown class type: $screen.cls")
                    }
                }
            }
        }
    }

    private fun openAnalyzeCompleteDialog() {
        val layout = layoutInflater.inflate(R.layout.dialog_analyze_complete, null)
        val dialog = AlertDialog.Builder(this)
            .setView(layout)
            .create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
        layout.findViewById<ImageView>(R.id.image_flag).attachCompatAnim(R.drawable.avd_flag_cross)
        layout.findViewById<ImageView>(R.id.image_ok).attachCompatAnim(R.drawable.avd_complete)
        dialog.show()
    }

    private fun testDiaryMockBundle(): Bundle {
        return bundleOf(
            EditDiaryActivity.EXTRA_INITIAL_DIARY to "안녕하세요. 테스트 다이어리입니다."
        )
    }

    private fun passwordSetBundle(): Bundle {
        return bundleOf(
            PasswordActivity.EXTRA_MODE to PasswordActivityMode.SET
        )
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