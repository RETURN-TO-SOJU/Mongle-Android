package com.won983212.mongle.presentation.view.dialog

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.won983212.mongle.databinding.DialogUnlockByPasswordBinding

class UnlockByPasswordDialog(
    context: Context,
    private val onClickOK: View.OnClickListener? = null
) : MongleDialog(context) {
    override fun open(): AlertDialog {
        val layout = DialogUnlockByPasswordBinding.inflate(
            LayoutInflater.from(context),
            null, false
        )

        val dialog = openDialog(layout.root, true, false)
        layout.imgUnlock.setOnClickListener {
            // TODO Animation 추가
            onClickOK?.onClick(it)
            dialog.dismiss()
        }
        return dialog
    }
}