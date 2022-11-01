package com.rtsoju.mongle.debug.view

import android.os.Bundle
import androidx.core.os.bundleOf
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.rtsoju.mongle.R
import com.rtsoju.mongle.domain.model.Emotion
import com.rtsoju.mongle.presentation.util.toastShort
import com.rtsoju.mongle.presentation.view.IntegrationFragment
import com.rtsoju.mongle.presentation.view.agree.AgreeActivity
import com.rtsoju.mongle.presentation.view.daydetail.DayDetailActivity
import com.rtsoju.mongle.presentation.view.dialog.*
import com.rtsoju.mongle.presentation.view.diary.EditDiaryActivity
import com.rtsoju.mongle.presentation.view.kakaoexport.KakaoExportActivity
import com.rtsoju.mongle.presentation.view.login.LoginActivity
import com.rtsoju.mongle.presentation.view.main.MainActivity
import com.rtsoju.mongle.presentation.view.newfavorite.NewFavoriteFragment
import com.rtsoju.mongle.presentation.view.password.PasswordActivity
import com.rtsoju.mongle.presentation.view.setemotion.SetEmotionFragment
import com.rtsoju.mongle.presentation.view.tutorial.TutorialActivity
import java.time.LocalDate

class ViewTestActivity : BaseTestActivity() {
    override val listItems: Array<IScreenInfo> = arrayOf(
        ActivityInfo("로그인", LoginActivity::class.java),
        ActivityInfo("이용 약관", AgreeActivity::class.java),
        ActivityInfo("메인", MainActivity::class.java),
        ActivityInfo("분석된 캘린더 상세", DayDetailActivity::class.java),
        ActivityInfo("비밀번호 입력", PasswordActivity::class.java),
        ActivityInfo("비밀번호 설정", PasswordActivity::class.java, passwordSetBundle()),
        FragmentInfo("계정 연동", this::integrationFragmentFactory),
        ActivityInfo("카톡 튜토리얼", TutorialActivity::class.java, makeKakaoTutorialBundle()),
        ActivityInfo("보안 튜토리얼", TutorialActivity::class.java, makeSecurityTutorialBundle()),
        FragmentInfo("찜 추가", this::newFavoriteFragmentFactory),
        ActivityInfo("카카오 카톡 데이터 전송", KakaoExportActivity::class.java),
        ActivityInfo("일기 작성", EditDiaryActivity::class.java, testDiaryMockBundle()),
        ManualInfo("분석 완료 다이얼로그") {
            AnalyzeCompleteDialog(
                this,
                "소마",
                "2022.08.01 ~ 2022.09.11"
            ).open()
        },
        ManualInfo("선물 도착 다이얼로그") { GiftArrivedDialog(this, "2022.08.01").open() },
        ManualInfo("로딩 다이얼로그") { LoadingDialog(this).open() },
        ManualInfo("비밀번호 입력 다이얼로그") {
            InputPasswordDialog(this)
                .setOnSubmitPassword { toastShort(it) }
                .open()
        },
        ManualInfo("잠금 해제 다이얼로그") {
            UnlockByPasswordDialog(this)
                .setOnClickOkListener { toastShort("Let's us unlock!") }
                .open()
        },
        FragmentInfo("감정 설정", this::setEmotionFragmentFactory),
    )

    private fun testDiaryMockBundle(): Bundle {
        return bundleOf(
            EditDiaryActivity.EXTRA_INITIAL_DIARY to "안녕하세요. 테스트 다이어리입니다."
        )
    }

    private fun passwordSetBundle(): Bundle {
        return bundleOf(
            PasswordActivity.EXTRA_MODE to PasswordActivity.Mode.SET
        )
    }

    private fun makeSecurityTutorialBundle(): Bundle {
        return bundleOf(
            TutorialActivity.EXTRA_TITLE_LIST_RES to R.array.security_tutorial_title,
            TutorialActivity.EXTRA_SUBTITLE_LIST_RES to R.array.security_tutorial_subtitle,
            TutorialActivity.EXTRA_IMAGE_LIST_RES to R.array.security_tutorial_image,
            TutorialActivity.EXTRA_IMAGE_WIDTH to 190f
        )
    }

    private fun makeKakaoTutorialBundle(): Bundle {
        return bundleOf(
            TutorialActivity.EXTRA_TITLE_LIST_RES to R.array.kakao_tutorial_title,
            TutorialActivity.EXTRA_IMAGE_LIST_RES to R.array.kakao_tutorial_image
        )
    }

    private fun integrationFragmentFactory(): BottomSheetDialogFragment {
        return IntegrationFragment()
    }

    private fun newFavoriteFragmentFactory(): BottomSheetDialogFragment {
        return NewFavoriteFragment.newInstance(Emotion.ANXIOUS)
    }

    private fun setEmotionFragmentFactory(): BottomSheetDialogFragment {
        return SetEmotionFragment.newInstance(LocalDate.now(), Emotion.ANXIOUS)
    }
}