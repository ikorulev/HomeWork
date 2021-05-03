package ru.ikorulev.homework

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FilmItem(val filmNumber: Int, val  filmTitle: String, val filmImage: Int, var filmFavorite: Boolean): Parcelable