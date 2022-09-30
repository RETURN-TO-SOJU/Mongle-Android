package com.won983212.mongle.presentation.view.dialog

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.won983212.mongle.databinding.DialogInputRoomNameBinding
import com.won983212.mongle.presentation.util.toastShort

class InputRoomNameDialog(
    context: Context,
    private val initialName: String,
    private val onSubmitName: OnDialogSubmit<String>? = null
) : MongleDialog(context) {
    override fun open(): AlertDialog {
        val layout = DialogInputRoomNameBinding.inflate(
            LayoutInflater.from(context),
            null, false
        )

        val dialog = openDialog(layout.root, true, false)
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
}