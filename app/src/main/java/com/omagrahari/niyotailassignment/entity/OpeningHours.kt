package com.omagrahari.niyotailassignment.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OpeningHours(
    val open_now: Boolean
) : Parcelable