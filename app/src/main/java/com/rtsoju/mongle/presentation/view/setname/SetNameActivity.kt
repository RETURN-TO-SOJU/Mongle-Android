package com.rtsoju.mongle.presentation.view.setname

import android.content.Intent
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.rtsoju.mongle.R
import com.rtsoju.mongle.databinding.ActivitySetNameBinding
import com.rtsoju.mongle.presentation.base.BaseDataActivity
import com.rtsoju.mongle.presentation.util.getParcelableExtraCompat
import com.rtsoju.mongle.presentation.view.agree.AgreeActivity.Companion.EXTRA_REDIRECT_TO
import com.rtsoju.mongle.presentation.view.login.LoginActivity.Companion.EXTRA_REDIRECT_TO
import com.rtsoju.mongle.presentation.view.setname.SetNameActivity.Companion.EXTRA_REDIRECT_TO
import com.rtsoju.mongle.presentation.view.setname.SetNameActivity.Companion.EXTRA_SHOW_TUTORIAL
import com.rtsoju.mongle.presentation.view.tutorial.TutorialActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * ## Extras
 * * **(선택)** [EXTRA_REDIRECT_TO]: [Intent] -
 * NameSetting과정이 끝나면, 지정한 intent를 startActivity
 *
 * * **(선택)** [EXTRA_SHOW_TUTORIAL]: [Boolean] -
 * 네임 설정을 마친 후 tutorial을 보여줄지 설정한다. 기본값은 false
 */
@AndroidEntryPoint
class SetNameActivity : BaseDataActivity<ActivitySetNameBinding>() {

    override val layoutId: Int = R.layout.activity_set_name
    private val viewModel by viewModels<SetNameViewModel>()

    override fun onInitialize() {
        binding.viewModel = viewModel

        val redirectTo: Intent? = intent.getParcelableExtraCompat(EXTRA_REDIRECT_TO)
        val showTutorial = intent.getBooleanExtra(EXTRA_SHOW_TUTORIAL, false)
        binding.btnNameSettingOk.setOnClickListener {
            lifecycleScope.launch {
                viewModel.updateUsername()
                withContext(Dispatchers.Main) {
                    if (redirectTo != null) {
                        startActivity(redirectTo)
                    }
                    if (showTutorial) {
                        TutorialActivity.startSecurityTutorial(this@SetNameActivity)
                    }
                    finish()
                }
            }
        }
    }

    companion object {
        const val EXTRA_REDIRECT_TO = "redirectTo"
        const val EXTRA_SHOW_TUTORIAL = "showTutorial"
    }
}