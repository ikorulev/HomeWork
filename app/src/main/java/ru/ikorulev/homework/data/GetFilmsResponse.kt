package ru.ikorulev.homework.data

import com.google.gson.annotations.SerializedName

data class GetFilmsResults (
    @SerializedName("page") val page: Int,
    @SerializedName("results") val movies: List<FilmItem>,
    @SerializedName("total_pages") val pages: Int
)