package ru.ikorulev.homework.data.tmdb

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TMDbService {

    @GET("movie/popular")
    suspend fun getPopularFilms(
        @Query("api_key") apiKey: String = "a059daa1d5fcf26a604426de0904c0f7",
        @Query("language") language: String = "ru",
        @Query("page") page: Int = 1
    ): GetFilmsResults

}