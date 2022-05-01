package com.geekbrains.androidstart.notes

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Note(
    var name: String,
    var description: String,
    var date: String
) : Parcelable
