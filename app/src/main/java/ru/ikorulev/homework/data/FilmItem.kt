package ru.ikorulev.homework.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class FilmItem(

    val filmId: Int,
    val filmTitle: String,
    val filmPath: String,
    val filmDetails: String,

    var isSelected: Boolean,
    var isFavorite: Boolean,

    var isWatchLater: Boolean,
    var watchDate: Date

) : Parcelable