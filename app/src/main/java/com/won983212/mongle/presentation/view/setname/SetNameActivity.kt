package com.won983212.mongle.presentation.view.setname

import android.content.Intent
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.won983212.mongle.R
import com.won983212.mongle.databinding.ActivitySetNameBinding
import com.won983212.mongle.presentation.base.BaseDataActivity
import com.won983212.mongle.presentation.util.getParcelableExtraCompat
import com.won983212.mongle.presentation.view.agree.AgreeActivity.Companion.EXTRA_REDIRECT_TO
import com.won983212.mongle.presentation.view.login.LoginActivity
import com.won983212.mongle.presentation.view.login.LoginActivity.Companion.EXTRA_REDIRECT_TO
import com.won983212.mongle.presentation.view.setname.SetNameActivity.Companion.EXTRA_REDIRECT_TO
import com.won983212.mongle.presentation.view.setname.SetNameActivity.Companion.EXTRA_SHOW_TUTORIAL
import com.won983212.mongle.presentation.view.tutorial.TutorialActivity
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

        val redirectTo: Intent? = intent.getParcelableExtraCompat(LoginActivity.EXTRA_REDIRECT_TO)
        val showTutorial = intent.getBooleanExtra(LoginActivity.EXTRA_REDIRECT_TO, false)
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