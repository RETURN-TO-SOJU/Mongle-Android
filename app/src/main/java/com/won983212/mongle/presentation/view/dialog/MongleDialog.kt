package com.won983212.mongle.presentation.view.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import androidx.appcompat.app.AlertDialog

abstract class MongleDialog(protected val context: Context) {
    abstract fun open(): AlertDialog

    protected fun openDialog(
        view: View,
        hideBackground: Boolean = false,
        cancelable: Boolean = true
    ): AlertDialog {
        val dialog = AlertDialog.Builder(context)
            .setView(view)
            .setCancelable(cancelable)
            .create()
        if (hideBackground) {
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        dialog.show()
        return dialog
    }

    fun interface OnDialogSubmit<T> {
        fun onSubmit(data: T)
    }
}