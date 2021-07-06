package ru.ikorulev.homework.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FilmItem(

    val filmTitle: String,
    val filmPath: String,
    val filmDetails: String,

    var isSelected: Boolean,
    var isFavorite: Boolean

) : Parcelable