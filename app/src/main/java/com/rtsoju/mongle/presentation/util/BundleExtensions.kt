package com.rtsoju.mongle.presentation.util

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Parcelable


internal inline fun <reified T : java.io.Serializable> Intent.getSerializableExtraCompat(
    name: String
): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getSerializableExtra(name, T::class.java)
    } else {
        @Suppress("DEPRECATION", "UNCHECKED_CAST")
        getSerializableExtra(name) as? T
    }
}

internal inline fun <reified T : java.io.Serializable> Intent.getSerializableExtraCompat(
    name: String,
    defaultValue: T
): T {
    return getSerializableExtraCompat(name) ?: defaultValue
}


internal inline fun <reified T : Parcelable> Intent.getParcelableExtraCompat(
    name: String
): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getParcelableExtra(name, T::class.java)
    } else {
        @Suppress("DEPRECATION")
        getParcelableExtra(name) as? T
    }
}

internal inline fun <reified T : Parcelable> Intent.getParcelableExtraCompat(
    name: String,
    defaultValue: T
): T {
    return getParcelableExtraCompat(name) ?: defaultValue
}


internal inline fun <reified T : java.io.Serializable> Bundle.getSerializableCompat(
    name: String
): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getSerializable(name, T::class.java)
    } else {
        @Suppress("DEPRECATION", "UNCHECKED_CAST")
        getSerializable(name) as? T
    }
}

internal inline fun <reified T : java.io.Serializable> Bundle.getSerializableCompat(
    name: String,
    defaultValue: T
): T {
    return getSerializableCompat(name) ?: defaultValue
}


internal inline fun <reified T : Parcelable> Bundle.getParcelableCompat(
    name: String
): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getParcelable(name, T::class.java)
    } else {
        @Suppress("DEPRECATION")
        getParcelable(name) as? T
    }
}

internal inline fun <reified T : Parcelable> Bundle.getParcelableCompat(
    name: String,
    defaultValue: T
): T {
    return getParcelableCompat(name) ?: defaultValue
}