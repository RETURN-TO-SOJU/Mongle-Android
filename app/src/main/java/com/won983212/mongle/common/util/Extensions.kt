package com.won983212.mongle.common.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.vectordrawable.graphics.drawable.Animatable2Compat
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat


internal fun Context.getColorCompat(@ColorRes color: Int) = ContextCompat.getColor(this, color)

internal fun TextView.setTextColorRes(@ColorRes color: Int) =
    setTextColor(context.getColorCompat(color))

internal fun ImageView.attachCompatAnim(@DrawableRes avdRes: Int) {
    val avd = AnimatedVectorDrawableCompat.create(context, avdRes)
    avd?.let {
        this.setImageDrawable(avd)
        it.start()
    }
}

internal fun ImageView.attachCompatAnimLoop(@DrawableRes avdRes: Int) {
    val avd = AnimatedVectorDrawableCompat.create(context, avdRes)
    avd?.let {
        it.registerAnimationCallback(object : Animatable2Compat.AnimationCallback() {
            override fun onAnimationEnd(drawable: Drawable?) {
                this@attachCompatAnimLoop.post { avd.start() }
            }
        })
        this.setImageDrawable(avd)
        it.start()
    }
}

internal fun <T> MutableLiveData<T>.asLiveData(): LiveData<T> = this

internal fun Context.toastShort(text: CharSequence) =
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()

internal fun Context.toastShort(@StringRes textRes: Int) =
    Toast.makeText(this, textRes, Toast.LENGTH_SHORT).show()

internal fun Context.toastLong(text: CharSequence) =
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()

internal fun Context.toastLong(@StringRes textRes: Int) =
    Toast.makeText(this, textRes, Toast.LENGTH_LONG).show()