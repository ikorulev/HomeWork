package ru.ikorulev.homework.data.tmdb

import com.google.gson.annotations.SerializedName

data class GetFilmsResults (
    @SerializedName("page") val page: Int,
    @SerializedName("results") val movies: List<FilmJson>,
    @SerializedName("total_pages") val pages: Int
)