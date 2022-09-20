package com.won983212.mongle.presentation.view

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import com.won983212.mongle.R
import com.won983212.mongle.presentation.util.attachCompatVectorAnim
import com.won983212.mongle.presentation.util.startAnim
import com.won983212.mongle.databinding.DialogAnalyzeCompleteBinding
import com.won983212.mongle.databinding.DialogArrivedGiftBinding
import com.won983212.mongle.databinding.DialogInputRoomNameBinding
import com.won983212.mongle.databinding.DialogLoadingBinding
import com.won983212.mongle.databinding.DialogTermsOfServiceDetailBinding
import com.won983212.mongle.presentation.util.toastShort


fun interface OnDialogSubmit<T> {
    fun onSubmit(data: T)
}


private fun openDialog(
    context: Context,
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

fun openTermsOfServiceDialog(context: Context, @StringRes contentId: Int): AlertDialog {
    val layout = DialogTermsOfServiceDetailBinding.inflate(
        LayoutInflater.from(context),
        null, false
    )
    layout.textTermsOfServiceDetail.movementMethod = ScrollingMovementMethod()
    layout.textTermsOfServiceDetail.setText(contentId)
    return openDialog(context, layout.root)
}

fun openLoadingDialog(context: Context): AlertDialog {
    val layout = DialogLoadingBinding.inflate(
        LayoutInflater.from(context),
        null, false
    )
    layout.imageLoading.startAnim(true)
    return openDialog(context, layout.root, true)
}

fun openAnalyzeCompleteDialog(
    context: Context,
    name: String,
    dateRange: String,
    onClickOK: View.OnClickListener? = null
): AlertDialog {
    val layout = DialogAnalyzeCompleteBinding.inflate(
        LayoutInflater.from(context),
        null, false
    )
    val dialog = openDialog(context, layout.root, true)
    layout.imageAnalyzeCompleteFlag.attachCompatVectorAnim(R.drawable.avd_flag_cross)
    layout.imageAnalyzeCompleteChecking.attachCompatVectorAnim(R.drawable.avd_complete)
    layout.textAnalyzeCompleteTitle.text =
        context.resources.getString(R.string.dialog_analyze_complete, name)
    layout.textAnalyzeCompleteDateRange.text = dateRange
    layout.btnAnalyzeCompleteGoResult.setOnClickListener {
        onClickOK?.onClick(it)
        dialog.dismiss()
    }
    return dialog
}

fun openGiftArrivedDialog(
    context: Context,
    date: String,
    onClickOK: View.OnClickListener? = null
): AlertDialog {
    val layout = DialogArrivedGiftBinding.inflate(
        LayoutInflater.from(context),
        null, false
    )

    val dialog = openDialog(context, layout.root, true)
    layout.imageArrivedGift.attachCompatVectorAnim(R.drawable.avd_gift)
    layout.textArrivedGiftDate.text = date
    layout.btnArrivedGiftWatchNow.setOnClickListener {
        onClickOK?.onClick(it)
        dialog.dismiss()
    }
    return dialog
}

fun openInputRoomNameDialog(
    context: Context,
    initialName: String,
    onSubmitName: OnDialogSubmit<String>? = null
): AlertDialog {
    val layout = DialogInputRoomNameBinding.inflate(
        LayoutInflater.from(context),
        null, false
    )

    val dialog = openDialog(context, layout.root, true, false)
    layout.fieldInputRoomName.setText(initialName.trim())
    layout.btnInputRoomNameOk.setOnClickListener {
        val roomName = layout.fieldInputRoomName.text.trim()
        if (roomName.isEmpty()) {
            context.toastShort("카톡 방(또는 상대) 이름을 입력해야합니다.")
        } else {
            onSubmitName?.onSubmit(roomName.toString())
            dialog.dismiss()
        }
    }
    return dialog
}