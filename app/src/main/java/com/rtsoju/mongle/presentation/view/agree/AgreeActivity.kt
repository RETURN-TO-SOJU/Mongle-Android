package com.rtsoju.mongle.presentation.view.agree

import android.content.Intent
import android.net.Uri
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.rtsoju.mongle.R
import com.rtsoju.mongle.databinding.ActivityAgreeBinding
import com.rtsoju.mongle.databinding.ListitemAgreeBinding
import com.rtsoju.mongle.presentation.base.BaseDataActivity
import com.rtsoju.mongle.presentation.util.getParcelableExtraCompat
import com.rtsoju.mongle.presentation.view.agree.AgreeActivity.Companion.EXTRA_REDIRECT_TO
import com.rtsoju.mongle.presentation.view.dialog.TermsOfServiceDialog
import com.rtsoju.mongle.presentation.view.login.LoginActivity
import com.rtsoju.mongle.presentation.view.login.LoginActivity.Companion.EXTRA_REDIRECT_TO
import com.rtsoju.mongle.presentation.view.setname.SetNameActivity

/**
 * ## Extras
 * * **(선택)** [EXTRA_REDIRECT_TO]: [Intent] -
 * Agree과정이 끝나면, 지정한 intent를 startActivity
 */
class AgreeActivity : BaseDataActivity<ActivityAgreeBinding>() {

    private val viewModel by viewModels<AgreeViewModel>()
    override val layoutId: Int = R.layout.activity_agree

    override fun onInitialize() {
        binding.viewModel = viewModel

        binding.cbxAgreeAll.setOnClickListener {
            viewModel.setAllAgreeChecked(binding.cbxAgreeAll.isChecked)
        }

        val redirectTo: Intent? = intent.getParcelableExtraCompat(LoginActivity.EXTRA_REDIRECT_TO)
        binding.btnAgreeOk.setOnClickListener {
            Intent(this, SetNameActivity::class.java).apply {
                putExtra(SetNameActivity.EXTRA_REDIRECT_TO, redirectTo)
                putExtra(SetNameActivity.EXTRA_SHOW_TUTORIAL, true)
                startActivity(this)
            }
            finish()
        }

        viewModel.attachDefaultHandlers(this)
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
            TermsOfServiceDialog(this, contentId).open()
        }
    }

    companion object {
        const val EXTRA_REDIRECT_TO = "redirectTo"
    }
}