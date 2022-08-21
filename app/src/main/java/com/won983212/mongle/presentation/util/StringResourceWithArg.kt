package com.won983212.mongle.presentation.util

import android.content.Context
import androidx.annotation.StringRes
import androidx.core.text.parseAsHtml
import androidx.core.text.toHtml
import androidx.core.text.toSpanned

class StringResourceWithArg(
    @StringRes val resId: Int? = null,
    vararg val args: Any?
) {
    override fun toString(): String {
        return "StringResourceWithArg[resId=${resId}, args=${args.joinToString()}]"
    }

    fun toCharSequence(context: Context): CharSequence {
        if (resId == null) {
            return ""
        }

        val parsedArgs: Array<Any?> = arrayOf(*args)
        for (i in parsedArgs.indices) {
            val arg = parsedArgs[i]
            if (arg is StringResourceWithArg) {
                parsedArgs[i] = arg.toCharSequence(context)
            }
        }

        return context.getText(resId)
            .toSpanned()
            .toHtml()
            .format(*parsedArgs)
            .parseAsHtml()
    }
}