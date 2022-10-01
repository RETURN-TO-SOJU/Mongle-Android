package com.won983212.mongle.presentation.view.setting

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import com.won983212.mongle.databinding.FragmentSettingBinding
import com.won983212.mongle.presentation.util.toastShort
import com.won983212.mongle.presentation.view.LeavingFragment
import com.won983212.mongle.presentation.view.dialog.InputPasswordDialog
import com.won983212.mongle.presentation.view.login.LoginActivity
import com.won983212.mongle.presentation.view.password.PasswordActivity
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

        binding.layoutSettingEncryptPassword.setOnClickListener {
            InputPasswordDialog(activity) {
                viewModel.setPasswordTo(it)
                activity.toastShort("암호키 비밀번호가 설정되었습니다.")
            }.open()
        }

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