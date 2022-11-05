package com.rtsoju.mongle.presentation.view.setting

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.rtsoju.mongle.R
import com.rtsoju.mongle.databinding.FragmentSettingBinding
import com.rtsoju.mongle.presentation.view.LeavingFragment
import com.rtsoju.mongle.presentation.view.password.PasswordActivity
import com.rtsoju.mongle.presentation.view.setname.SetNameActivity
import com.rtsoju.mongle.presentation.view.starting.StartingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment : Fragment() {

    private val viewModel by viewModels<SettingViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val activity = requireActivity()
        val binding = FragmentSettingBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val refreshUsername =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                viewModel.updateUsernameTitle()
            }

        binding.layoutSettingUsername.setOnClickListener {
            refreshUsername.launch(
                Intent(activity, SetNameActivity::class.java).apply {
                    putExtra(SetNameActivity.EXTRA_USE_BACK_FINISH, true)
                }
            )
        }

        activity.supportFragmentManager.setFragmentResultListener(
            LeavingFragment.REQUEST_KEY,
            viewLifecycleOwner
        ) { _, bundle ->
            val agreed = bundle.getBoolean(LeavingFragment.RESULT_AGREED, false)
            if (agreed) {
                viewModel.doLeave()
            }
        }

        binding.layoutSettingLeave.setOnClickListener {
            LeavingFragment.newInstance().apply {
                show(activity.supportFragmentManager, tag)
            }
        }

        binding.layoutSettingScreenPassword.setOnClickListener {
            Intent(activity, PasswordActivity::class.java).apply {
                putExtra(PasswordActivity.EXTRA_MODE, PasswordActivity.Mode.SET)
                startActivity(this)
            }
        }

        binding.layoutSettingQanda.setOnClickListener {
            goToURL(R.string.setting_url_qanda)
        }

        binding.layoutSettingPrivacyPolicy.setOnClickListener {
            goToURL(R.string.setting_url_privacy_policy)
        }

        binding.layoutSettingTermsOfService.setOnClickListener {
            goToURL(R.string.terms_of_service_url)
        }

        // TODO (LATER) 우선은 비밀번호 변경 api가 만들어지면 추가하도록 하자
        /*binding.layoutSettingEncryptPassword.setOnClickListener {
            InputPasswordDialog(activity) {
                viewModel.setPasswordTo(it)
                activity.toastShort("암호키 비밀번호가 설정되었습니다.")
            }.open()
        }*/

        viewModel.attachDefaultHandlers(activity)
        viewModel.eventLeaveAccount.observe(viewLifecycleOwner) {
            Intent(activity, StartingActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(this)
            }
        }

        updateUsernameTitle()
        return binding.root
    }

    private fun goToURL(@StringRes strRes: Int) {
        val url = resources.getString(strRes)
        Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
            startActivity(this)
        }
    }

    private fun updateUsernameTitle() {
        viewModel.updateUsernameTitle()
    }
}