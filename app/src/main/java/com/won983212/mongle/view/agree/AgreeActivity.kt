package com.won983212.mongle.view.agree

import android.content.Intent
import android.net.Uri
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.won983212.mongle.R
import com.won983212.mongle.common.base.BaseDataActivity
import com.won983212.mongle.databinding.ActivityAgreeBinding
import com.won983212.mongle.databinding.ListitemAgreeBinding
import com.won983212.mongle.view.openTermsOfServiceDialog
import com.won983212.mongle.view.overview.OverviewActivity
import com.won983212.mongle.view.tutorial.TutorialActivity

class AgreeActivity : BaseDataActivity<ActivityAgreeBinding>() {
    private val viewModel by viewModels<AgreeViewModel>()

    override val layoutId: Int = R.layout.activity_agree

    override fun onInitialize() {
        binding.viewModel = viewModel

        binding.cbxAgreeAll.setOnClickListener {
            viewModel.setAllAgreeChecked(binding.cbxAgreeAll.isChecked)
        }

        binding.btnAgreeOk.setOnClickListener {
            Intent(this, OverviewActivity::class.java).apply {
                startActivity(this)
            }
            TutorialActivity.startKakaoTutorial(this)
            finish()
        }

        viewModel.attachDefaultErrorHandler(this)
        initAgreeItems()
    }

    private fun initAgreeItems() {
        val titles = resources.obtainTypedArray(R.array.terms_of_service_titles)
        for (i in 0 until titles.length()) {
            DataBindingUtil.inflate<ListitemAgreeBinding>(
                layoutInflater,
                R.layout.listitem_agree,
                binding.layoutAgreeList,
                true
            ).apply {
                viewModel = this@AgreeActivity.viewModel
                lifecycleOwner = this@AgreeActivity
                index = i
                textAgreeTitle.setText(titles.getResourceId(i, 0))
                textAgreeTitle.setOnClickListener { showDetail(i) }
                cbxAgree.setOnClickListener {
                    this@AgreeActivity.viewModel.setAgreeChecked(i, cbxAgree.isChecked)
                }
            }
        }
        titles.recycle()
    }

    private fun showDetail(at: Int) {
        val contents = resources.obtainTypedArray(R.array.terms_of_service_contents)
        val contentId = contents.getResourceId(at, 0)
        val contentText = resources.getText(contentId)

        contents.recycle()
        if (contentText.startsWith("http://") || contentText.startsWith("https://")) {
            Intent(Intent.ACTION_VIEW, Uri.parse(contentText.toString())).apply {
                startActivity(this)
            }
        } else {
            openTermsOfServiceDialog(this, contentId)
        }
    }
}