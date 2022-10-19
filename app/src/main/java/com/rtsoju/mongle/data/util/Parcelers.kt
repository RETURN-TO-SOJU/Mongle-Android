package com.rtsoju.mongle.data.util

import android.os.Parcel
import kotlinx.parcelize.Parceler
import java.time.LocalDate

object LocalDateParceler : Parceler<LocalDate> {
    override fun create(parcel: Parcel): LocalDate =
        LocalDate.of(parcel.readInt(), parcel.readInt(), parcel.readInt())

    override fun LocalDate.write(parcel: Parcel, flags: Int) {
        parcel.writeInt(year)
        parcel.writeInt(monthValue)
        parcel.writeInt(dayOfMonth)
    }
}