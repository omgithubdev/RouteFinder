package com.omagrahari.niyotailassignment.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Viewport(
    var northeast: Northeast?,
    var southwest: Southwest?
) : Parcelable