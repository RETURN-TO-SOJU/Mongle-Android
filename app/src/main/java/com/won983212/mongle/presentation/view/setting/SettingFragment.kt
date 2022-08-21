package com.won983212.mongle.presentation.view.setting

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.won983212.mongle.databinding.FragmentSettingBinding
import com.won983212.mongle.presentation.view.password.PasswordActivity
import com.won983212.mongle.presentation.view.password.PasswordActivityMode
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment : Fragment() {

    private val viewModel by viewModels<SettingViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSettingBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.layoutSettingLeave.setOnClickListener {

        }

        binding.layoutSettingPasswordSetup.setOnClickListener {
            Intent(requireContext(), PasswordActivity::class.java).apply {
                putExtra(PasswordActivity.EXTRA_MODE, PasswordActivityMode.SET)
                startActivity(this)
            }
        }

        updateUsernameTitle()
        return binding.root
    }

    fun updateUsernameTitle() {
        viewModel.updateUsernameTitle()
    }
}