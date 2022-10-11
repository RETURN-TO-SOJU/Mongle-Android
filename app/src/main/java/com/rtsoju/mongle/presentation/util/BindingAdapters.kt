package com.rtsoju.mongle.presentation.util

import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.databinding.BindingAdapter
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.rtsoju.mongle.presentation.base.BaseRecyclerAdapter

@BindingAdapter("drawableStartRes")
fun setDrawableStartRes(view: EditText, @DrawableRes data: Int) {
    view.setCompoundDrawablesWithIntrinsicBounds(
        data,
        0,
        0,
        0
    )
}

@BindingAdapter("imgRes")
fun setImageRes(view: ImageView, @DrawableRes data: Int) {
    view.setImageResource(data)
    view.setImageResource(data)
}

@Suppress("UNCHECKED_CAST")
@BindingAdapter("items")
fun setItems(view: RecyclerView, data: List<Any>?) {
    val adapter = view.adapter as? BaseRecyclerAdapter<ViewDataBinding, Any>?
    adapter?.set(data ?: emptyList())
}

@BindingAdapter("textRes")
fun setTextAsRes(view: TextView, @StringRes data: Int?) {
    if (data != null && data != 0) {
        view.setText(data)
    }
}

@BindingAdapter("textResource")
fun setTextAsIdWithArguments(view: TextView, data: TextResource?) {
    data?.let {
        view.text = data.toCharSequence(view.context)
    }
}