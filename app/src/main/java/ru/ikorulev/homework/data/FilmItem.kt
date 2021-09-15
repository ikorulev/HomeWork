package ru.ikorulev.homework.data

import java.util.*

data class FilmItem(

    val filmId: Int,
    val filmTitle: String,
    val filmPath: String,
    val filmDetails: String,

    var isSelected: Boolean,
    var isFavorite: Boolean,

    var isWatchLater: Boolean,
    var watchDate: Date

)