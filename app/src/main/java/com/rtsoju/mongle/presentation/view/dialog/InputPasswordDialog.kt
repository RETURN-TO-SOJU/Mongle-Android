package com.rtsoju.mongle.presentation.view.dialog

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.rtsoju.mongle.R
import com.rtsoju.mongle.databinding.DialogInputPasswordBinding
import com.rtsoju.mongle.presentation.util.toastShort
import java.util.regex.Pattern

class InputPasswordDialog(
    context: Context
) : MongleDialog(context) {

    private var onSubmitPassword: OnDialogSubmit<String>? = null

    fun setOnSubmitPassword(listener: OnDialogSubmit<String>): InputPasswordDialog {
        onSubmitPassword = listener
        return this
    }

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
            } else if (!PASSWORD_REGEX.matcher(pwd).matches()) {
                context.toastShort("비밀번호는 영문, 숫자, 특수문자로만 되어있어야합니다. 공백도 안됩니다.")
            } else {
                AlertMessageDialog(
                    context,
                    R.string.pwd_input_confirm_message_title,
                    R.string.pwd_input_confirm_message_subtitle
                )
                    .setOnDialogResultListener {
                        if (it == AlertMessageDialog.Result.OK) {
                            onSubmitPassword?.onSubmit(pwd.toString())
                            dialog.dismiss()
                        }
                    }
                    .open()
            }
        }
        layout.btnInputPasswordCancel.setOnClickListener {
            AlertMessageDialog(
                context,
                R.string.pwd_input_nopwd_message_title,
                R.string.pwd_input_nopwd_message_subtitle
            )
                .setOnDialogResultListener {
                    if (it == AlertMessageDialog.Result.OK) {
                        onSubmitPassword?.onSubmit(DEFAULT_PASSWORD)
                        dialog.dismiss()
                    }
                }
                .open()
        }
        return dialog
    }

    companion object {
        private const val DEFAULT_PASSWORD = "0000"
        private val PASSWORD_REGEX =
            Pattern.compile("[a-zA-Z0-9{}\\[\\]/?.,;:|)*~`!^\\-_+<>@#\$%&\\\\=('\"]+")
    }
}