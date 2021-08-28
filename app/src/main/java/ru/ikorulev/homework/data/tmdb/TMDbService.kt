package ru.ikorulev.homework.data.tmdb

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface TMDbService {
    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val API_KEY = "a059daa1d5fcf26a604426de0904c0f7"
    }

    @GET("movie/popular")
    fun getPopularFilms(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "ru",
        @Query("page") page: Int = 1
    ): Single<GetFilmsResults>?

}