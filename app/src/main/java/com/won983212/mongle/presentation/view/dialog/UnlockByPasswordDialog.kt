package com.won983212.mongle.presentation.view.dialog

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.won983212.mongle.R
import com.won983212.mongle.databinding.DialogUnlockByPasswordBinding
import com.won983212.mongle.presentation.util.attachCompatVectorAnim

class UnlockByPasswordDialog(
    context: Context,
    private val onClickOK: View.OnClickListener? = null
) : MongleDialog(context) {
    private var isUnlocking = false

    override fun open(): AlertDialog {
        val layout = DialogUnlockByPasswordBinding.inflate(
            LayoutInflater.from(context),
            null, false
        )

        val dialog = openDialog(layout.root, true, false)
        layout.imgUnlock.setOnClickListener {
            if(!isUnlocking) {
                isUnlocking = true
                layout.imgUnlock.attachCompatVectorAnim(R.drawable.avd_unlock, false) {
                    onClickOK?.onClick(it)
                    dialog.dismiss()
                }
            }
        }
        return dialog
    }
}