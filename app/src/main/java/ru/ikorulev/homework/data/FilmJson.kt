package ru.ikorulev.homework.data

import com.google.gson.annotations.SerializedName

data class FilmJson (

    @SerializedName("title") val filmTitle: String?,
    @SerializedName("backdrop_path") val filmPath: String?,
    @SerializedName("overview") val filmDetails: String?

)