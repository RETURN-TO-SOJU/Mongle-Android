package com.rtsoju.mongle.presentation.view.dialog

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.rtsoju.mongle.R
import com.rtsoju.mongle.databinding.DialogArrivedGiftBinding
import com.rtsoju.mongle.presentation.util.attachCompatVectorAnim

class GiftArrivedDialog(
    context: Context,
    private val date: String
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
            dialog.dismiss()
        }
        return dialog
    }
}