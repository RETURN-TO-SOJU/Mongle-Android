package com.won983212.mongle.view

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import com.won983212.mongle.R
import com.won983212.mongle.common.util.attachCompatAnim
import com.won983212.mongle.common.util.startAnim

// TODO Refactoring

fun openLoadingDialog(context: Context): AlertDialog {
    val layout = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null)
    val dialog = AlertDialog.Builder(context)
        .setView(layout)
        .setCancelable(false)
        .create()
    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    layout.findViewById<ImageView>(R.id.image_loading).startAnim(true)
    dialog.show()
    return dialog
}

fun openAnalyzeCompleteDialog(context: Context): AlertDialog {
    val layout = LayoutInflater.from(context).inflate(R.layout.dialog_analyze_complete, null)
    val dialog = AlertDialog.Builder(context)
        .setView(layout)
        .create()
    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    layout.findViewById<ImageView>(R.id.image_analyze_complete_flag)
        .attachCompatAnim(R.drawable.avd_flag_cross)
    layout.findViewById<ImageView>(R.id.image_analyze_complete_checking)
        .attachCompatAnim(R.drawable.avd_complete)
    dialog.show()
    return dialog
}

fun openGiftArrivedDialog(context: Context): AlertDialog {
    val layout = LayoutInflater.from(context).inflate(R.layout.dialog_arrived_gift, null)
    val dialog = AlertDialog.Builder(context)
        .setView(layout)
        .create()
    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    layout.findViewById<ImageView>(R.id.image_arrived_gift)
        .attachCompatAnim(R.drawable.avd_gift)
    dialog.show()
    return dialog
}