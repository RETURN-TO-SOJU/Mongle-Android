package com.rtsoju.mongle.presentation.view.dialog

import android.content.Context
import android.view.LayoutInflater
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import com.rtsoju.mongle.R
import com.rtsoju.mongle.databinding.DialogMessageBinding

class AlertMessageDialog(
    context: Context,
    @StringRes
    private val title: Int,
    @StringRes
    private val description: Int,
    private val onDialogResult: OnDialogSubmit<Result>? = null,
    @DrawableRes
    private val alertImage: Int = R.drawable.img_warning
) : MongleDialog(context) {
    override fun open(): AlertDialog {
        val layout = DialogMessageBinding.inflate(
            LayoutInflater.from(context),
            null, false
        )

        val dialog = openDialog(layout.root, true, false)
        layout.btnMessageOk.setOnClickListener {
            onDialogResult?.onSubmit(Result.OK)
            dialog.dismiss()
        }
        layout.btnMessageCancel.setOnClickListener {
            onDialogResult?.onSubmit(Result.CANCEL)
            dialog.dismiss()
        }

        layout.imgMessage.setImageResource(alertImage)
        layout.textMessageTitle.setText(title)
        layout.textMessageDescription.setText(description)
        return dialog
    }

    enum class Result {
        OK, CANCEL
    }
}