package com.rtsoju.mongle.presentation.view.dialog

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.rtsoju.mongle.databinding.DialogInputRoomNameBinding
import com.rtsoju.mongle.presentation.util.toastShort

class InputRoomNameDialog(
    context: Context,
    private val initialName: String
) : MongleDialog(context) {

    private var onSubmitNameListener: OnDialogSubmit<String>? = null

    fun setOnSubmitName(listener: OnDialogSubmit<String>): InputRoomNameDialog {
        onSubmitNameListener = listener
        return this
    }

    override fun open(): AlertDialog {
        val layout = DialogInputRoomNameBinding.inflate(
            LayoutInflater.from(context),
            null, false
        )

        val dialog = openDialog(layout.root, true)
        layout.fieldInputRoomName.setText(initialName.trim())
        layout.btnInputRoomNameOk.setOnClickListener {
            val roomName = layout.fieldInputRoomName.text.trim()
            if (roomName.isEmpty()) {
                context.toastShort("카톡 방(또는 상대) 이름을 입력해야합니다.")
            } else {
                onSubmitNameListener?.onSubmit(roomName.toString())
                dialog.dismiss()
            }
        }
        return dialog
    }
}