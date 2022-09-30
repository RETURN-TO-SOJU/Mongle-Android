package com.won983212.mongle.presentation.view.dialog

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.won983212.mongle.R
import com.won983212.mongle.databinding.DialogInputPasswordBinding
import com.won983212.mongle.presentation.util.toastShort

class InputPasswordDialog(
    context: Context,
    private val onSubmitPassword: OnDialogSubmit<String>? = null
) : MongleDialog(context) {
    override fun open(): AlertDialog {
        val layout = DialogInputPasswordBinding.inflate(
            LayoutInflater.from(context),
            null, false
        )

        val dialog = openDialog(layout.root, true, false)
        layout.btnInputPasswordOk.setOnClickListener {
            val pwd = layout.fieldInputPassword.text.trim()
            if (pwd.isEmpty()) {
                context.toastShort("사용할 암호를 입력해주세요. 사용하고 싶지 않다면, '사용 안 함'을 눌러주세요.")
            } else {
                onSubmitPassword?.onSubmit(pwd.toString())
                dialog.dismiss()
            }
        }
        layout.btnInputPasswordCancel.setOnClickListener {
            AlertMessageDialog(
                context,
                R.string.pwd_input_nopwd_message_title,
                R.string.pwd_input_nopwd_message_subtitle,
                {
                    if (it == AlertMessageDialog.Result.OK) {
                        onSubmitPassword?.onSubmit(DEFAULT_PASSWORD)
                        dialog.dismiss()
                    }
                }
            ).open()
        }
        return dialog
    }

    companion object {
        private const val DEFAULT_PASSWORD = "0000"
    }
}