package com.won983212.mongle.presentation.bindingadapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.won983212.mongle.presentation.util.StringResourceWithArg


@BindingAdapter("text")
fun setTextAsIdWithArguments(view: TextView, data: StringResourceWithArg?) {
    data?.let {
        view.text = data.toCharSequence(view.context)
    }
}