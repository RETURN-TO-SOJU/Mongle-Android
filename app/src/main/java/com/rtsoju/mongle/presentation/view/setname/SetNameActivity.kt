package com.rtsoju.mongle.presentation.view.setname

import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.rtsoju.mongle.R
import com.rtsoju.mongle.databinding.ActivitySetNameBinding
import com.rtsoju.mongle.presentation.base.BaseDataActivity
import com.rtsoju.mongle.presentation.view.setname.SetNameActivity.Companion.EXTRA_USE_BACK_FINISH
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * ## Extras *
 * * **(선택)** [EXTRA_USE_BACK_FINISH]: [Boolean] -
 * 뒤로가기 버튼을 사용할지 정한다. 뒤로가기 버튼을 누르면 이 activity가 finish됨. 기본값은 false
 */
@AndroidEntryPoint
class SetNameActivity : BaseDataActivity<ActivitySetNameBinding>() {

    override val layoutId: Int = R.layout.activity_set_name
    private val viewModel by viewModels<SetNameViewModel>()

    override fun onInitialize() {
        binding.viewModel = viewModel

        binding.btnNameSettingOk.setOnClickListener {
            lifecycleScope.launch {
                viewModel.updateUsername()
                withContext(Dispatchers.Main) {
                    setResult(RESULT_OK)
                    finish()
                }
            }
        }

        val useBackFinish = intent.getBooleanExtra(EXTRA_USE_BACK_FINISH, false)
        if (useBackFinish) {
            binding.btnNameSettingBack.visibility = View.VISIBLE
            binding.btnNameSettingBack.setOnClickListener {
                finish()
            }
        }
    }

    companion object {
        const val EXTRA_USE_BACK_FINISH = "useBackFinish"
    }
}