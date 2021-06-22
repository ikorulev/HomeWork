package ru.ikorulev.homework

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FilmItem(
    val filmTitle: String,
    val filmDetails: String,
    val filmImage: Int,
    var isSelected: Boolean,
    var isFavorite: Boolean
) : Parcelable