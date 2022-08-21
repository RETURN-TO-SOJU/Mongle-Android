package com.won983212.mongle.presentation.bindingadapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.won983212.mongle.presentation.util.TextResource


@BindingAdapter("textResource")
fun setTextAsIdWithArguments(view: TextView, data: TextResource?) {
    data?.let {
        view.text = data.toCharSequence(view.context)
    }
}