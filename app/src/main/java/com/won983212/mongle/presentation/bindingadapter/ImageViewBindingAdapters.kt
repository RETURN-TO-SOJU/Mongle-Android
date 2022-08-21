package com.won983212.mongle.presentation.bindingadapter

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter

@BindingAdapter("imgRes")
fun setImageRes(view: ImageView, @DrawableRes data: Int) {
    view.setImageResource(data)
}