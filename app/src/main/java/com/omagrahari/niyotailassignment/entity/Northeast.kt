package com.omagrahari.niyotailassignment.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Northeast(
    val lat: Double,
    val lng: Double
) : Parcelable