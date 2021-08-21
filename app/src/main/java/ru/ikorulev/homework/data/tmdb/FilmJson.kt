package ru.ikorulev.homework.data.tmdb

import com.google.gson.annotations.SerializedName

data class FilmJson (

    @SerializedName("id") val filmId: Int,
    @SerializedName("title") val filmTitle: String?,
    @SerializedName("backdrop_path") val filmPath: String?,
    @SerializedName("overview") val filmDetails: String?

)