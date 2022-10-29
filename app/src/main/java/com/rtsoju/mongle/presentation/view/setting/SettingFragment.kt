package com.rtsoju.mongle.presentation.view.setting

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import com.rtsoju.mongle.databinding.FragmentSettingBinding
import com.rtsoju.mongle.presentation.view.LeavingFragment
import com.rtsoju.mongle.presentation.view.login.LoginActivity
import com.rtsoju.mongle.presentation.view.password.PasswordActivity
import com.rtsoju.mongle.presentation.view.setname.SetNameActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment : Fragment() {

    private val viewModel by viewModels<SettingViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener(LeavingFragment.REQUEST_KEY) { _, bundle ->
            val agreed = bundle.getBoolean(LeavingFragment.RESULT_AGREED, false)
            if (agreed) {
                viewModel.doLeave()
            }
        }
    }

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

        // TODO (LATER) 향후 leaving 구현
        /*binding.layoutSettingLeave.setOnClickListener {
            LeavingFragment.newInstance().apply {
                show(activity.supportFragmentManager, tag)
            }
        }*/

        binding.layoutSettingScreenPassword.setOnClickListener {
            Intent(activity, PasswordActivity::class.java).apply {
                putExtra(PasswordActivity.EXTRA_MODE, PasswordActivity.Mode.SET)
                startActivity(this)
            }
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
            Intent(activity, LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(this)
            }
        }

        updateUsernameTitle()
        return binding.root
    }

    private fun updateUsernameTitle() {
        viewModel.updateUsernameTitle()
    }
}