package com.won983212.mongle.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.won983212.mongle.R
import com.won983212.mongle.databinding.ActivityAgreeBinding
import com.won983212.mongle.databinding.DialogTermsOfServiceDetailBinding
import com.won983212.mongle.databinding.ListitemAgreeBinding
import com.won983212.mongle.view.tutorial.TutorialActivity
import com.won983212.mongle.viewmodel.AgreeViewModel

class AgreeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAgreeBinding
    private val viewModel: AgreeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_agree)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.cbxAgreeAll.setOnClickListener {
            viewModel.setAllAgreeChecked(binding.cbxAgreeAll.isChecked)
        }
        binding.btnAgreeOk.setOnClickListener {
            Intent(this, TutorialActivity::class.java).apply {
                putExtra(TutorialActivity.EXTRA_TITLE_LIST_RES, R.array.kakao_tutorial_title)
                putExtra(TutorialActivity.EXTRA_IMAGE_LIST_RES, R.array.kakao_tutorial_image)
                startActivity(this)
            }
        }
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
    }

    private fun showDetail(at: Int) {
        val contents = resources.obtainTypedArray(R.array.terms_of_service_contents)
        val contentId = contents.getResourceId(at, 0)
        val contentText = resources.getText(contentId)

        if (contentText.startsWith("http://") || contentText.startsWith("https://")) {
            Intent(Intent.ACTION_VIEW, Uri.parse(contentText.toString())).apply {
                startActivity(this)
            }
        } else {
            val dialogBinding =
                DialogTermsOfServiceDetailBinding.inflate(layoutInflater, null, false)
            val dialog = AlertDialog.Builder(this)
                .setView(dialogBinding.root)
                .create()
            dialogBinding.textTermsOfServiceDetail.movementMethod = ScrollingMovementMethod()
            dialogBinding.textTermsOfServiceDetail.setText(contentId)
            dialog.show()
        }
    }
}