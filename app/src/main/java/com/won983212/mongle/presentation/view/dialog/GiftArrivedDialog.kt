package com.won983212.mongle.presentation.view.dialog

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.won983212.mongle.R
import com.won983212.mongle.databinding.DialogArrivedGiftBinding
import com.won983212.mongle.presentation.util.attachCompatVectorAnim

class GiftArrivedDialog(
    context: Context,
    private val date: String,
    private val onClickOK: View.OnClickListener? = null
) : MongleDialog(context) {
    override fun open(): AlertDialog {
        val layout = DialogArrivedGiftBinding.inflate(
            LayoutInflater.from(context),
            null, false
        )

        val dialog = openDialog(layout.root, true)
        layout.imageArrivedGift.attachCompatVectorAnim(R.drawable.avd_gift)
        layout.textArrivedGiftDate.text = date
        layout.btnArrivedGiftWatchNow.setOnClickListener {
            onClickOK?.onClick(it)
            dialog.dismiss()
        }
        return dialog
    }
}