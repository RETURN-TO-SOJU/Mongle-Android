package com.rtsoju.mongle.presentation.view.dialog

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.rtsoju.mongle.R
import com.rtsoju.mongle.databinding.DialogAnalyzeCompleteBinding
import com.rtsoju.mongle.presentation.util.attachCompatVectorAnim

class AnalyzeCompleteDialog(
    context: Context,
    private val name: String,
    private val dateRange: String
) : MongleDialog(context) {

    override fun open(): AlertDialog {
        val layout = DialogAnalyzeCompleteBinding.inflate(
            LayoutInflater.from(context),
            null, false
        )
        val dialog = openDialog(layout.root, true)
        layout.imageAnalyzeCompleteFlag.attachCompatVectorAnim(R.drawable.avd_flag_cross)
        layout.imageAnalyzeCompleteChecking.attachCompatVectorAnim(R.drawable.avd_complete)
        layout.textAnalyzeCompleteTitle.text =
            context.resources.getString(R.string.dialog_analyze_complete, name)
        layout.textAnalyzeCompleteDateRange.text = dateRange
        layout.btnAnalyzeCompleteGoResult.setOnClickListener {
            dialog.dismiss()
        }
        return dialog
    }
}