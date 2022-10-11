package com.rtsoju.mongle.presentation.view.dialog

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.rtsoju.mongle.databinding.DialogLoadingBinding
import com.rtsoju.mongle.presentation.util.startAnim

class LoadingDialog(context: Context) : MongleDialog(context) {
    override fun open(): AlertDialog {
        val layout = DialogLoadingBinding.inflate(
            LayoutInflater.from(context),
            null, false
        )
        layout.imageLoading.startAnim(true)
        return openDialog(layout.root, true, false)
    }
}