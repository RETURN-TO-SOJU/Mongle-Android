package com.rtsoju.mongle.presentation.view.dialog

import android.content.Context
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import com.rtsoju.mongle.databinding.DialogTermsOfServiceDetailBinding

class TermsOfServiceDialog(
    context: Context,
    @StringRes private val contentId: Int
) : MongleDialog(context) {
    override fun open(): AlertDialog {
        val layout = DialogTermsOfServiceDetailBinding.inflate(
            LayoutInflater.from(context),
            null, false
        )
        layout.textTermsOfServiceDetail.movementMethod = ScrollingMovementMethod()
        layout.textTermsOfServiceDetail.setText(contentId)
        return openDialog(layout.root)
    }
}