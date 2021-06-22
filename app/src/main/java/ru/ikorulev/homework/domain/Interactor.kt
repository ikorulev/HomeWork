package ru.ikorulev.homework.domain

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.ikorulev.homework.data.FilmItem
import ru.ikorulev.homework.data.FilmsRepository
import ru.ikorulev.homework.data.GetFilmsResults
import ru.ikorulev.homework.data.TMDbService

class Interactor(
    private val tmDbService: TMDbService,
    private val filmsRepository: FilmsRepository
) {
    var items = mutableListOf<FilmItem>()

    fun getFilms(callback: GetFilmCallback) {
        tmDbService.getPopularFilms()
            .enqueue(object : Callback<GetFilmsResults> {
                override fun onFailure(call: Call<GetFilmsResults>, t: Throwable) {
                    Log.d("Interactor", "onFailure")
                }

                override fun onResponse(
                    call: Call<GetFilmsResults>,
                    response: Response<GetFilmsResults>
                ) {
                    Log.d("Interactor", "onResponse")
                    items.clear()
                    if (response.isSuccessful) {
                        response.body()?.movies?.forEach {
                            /*Log.d("Interactor", it.filmTitle)
                            Log.d("Interactor", it.filmImage)
                            Log.d("Interactor", it.filmDetails)*/

                            if (it.filmTitle != null && it.filmTitle.isNotEmpty()
                                && it.filmImage != null && it.filmImage.isNotEmpty()
                                && it.filmDetails != null && it.filmDetails.isNotEmpty()
                            ) {

                                items.add(
                                    FilmItem(
                                        //it.id,
                                        it.filmTitle,
                                        it.filmImage,
                                        it.filmDetails,

                                        false,
                                        false
                                    )
                                )
                            }
                        }
                    }
                    Log.d("Interactor", "")
                    filmsRepository.addToCache(items.toList())

                    callback.onSuccess(filmsRepository.cachedFilms)
                }
            })


    }

    interface GetFilmCallback {
        fun onSuccess(films: List<FilmItem>)
        fun onError(error: String)
    }
}