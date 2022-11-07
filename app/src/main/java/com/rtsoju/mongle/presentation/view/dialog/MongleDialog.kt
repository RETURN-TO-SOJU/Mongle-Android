package com.rtsoju.mongle.presentation.view.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import androidx.appcompat.app.AlertDialog

abstract class MongleDialog(protected val context: Context) {

    private var onCancelledListener: OnDialogCancelled? = null

    abstract fun open(): AlertDialog

    protected fun openDialog(
        view: View,
        hideBackground: Boolean = false,
        cancelable: Boolean = true
    ): AlertDialog {
        val builder = AlertDialog.Builder(context)
            .setView(view)
            .setCancelable(cancelable)

        if (onCancelledListener != null) {
            builder.setOnCancelListener { onCancelledListener?.onCancelled() }
        }

        val dialog = builder.create()
        if (hideBackground) {
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        dialog.show()

        return dialog
    }

    fun setOnCancelledListener(listener: OnDialogCancelled): MongleDialog {
        onCancelledListener = listener
        return this
    }

    fun interface OnDialogSubmit<T> {
        fun onSubmit(data: T)
    }

    fun interface OnDialogCancelled {
        fun onCancelled()
    }
}