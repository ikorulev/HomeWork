package ru.ikorulev.homework.domain

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.ikorulev.homework.App
import ru.ikorulev.homework.data.*

class Interactor(

    private val tmDbService: TMDbService

) {
    var itemsFilms = mutableListOf<FilmDb>()

    fun loadFilms(page: Int, callback: GetFilmCallback) {
        tmDbService.getPopularFilms(page = page)
            .enqueue(object : Callback<GetFilmsResults> {
                override fun onFailure(call: Call<GetFilmsResults>, t: Throwable) {
                    Log.d("Interactor", "onFailure")
                }

                override fun onResponse(
                    call: Call<GetFilmsResults>,
                    response: Response<GetFilmsResults>
                ) {

                    Log.d("Interactor", "onResponse")
                    itemsFilms.clear()
                    if (response.isSuccessful) {
                        response.body()?.movies?.forEach {
                            if (it.filmTitle != null && it.filmTitle.isNotEmpty()
                                && it.filmPath != null && it.filmPath.isNotEmpty()
                                && it.filmDetails != null && it.filmDetails.isNotEmpty()
                            ) {
                                itemsFilms.add(
                                    FilmDb(
                                        //it.id,
                                        it.filmTitle,
                                        it.filmPath,
                                        it.filmDetails
                                    )
                                )
                            }
                        }
                    }

                    Db.getInstance(App.instance.applicationContext)
                        ?.getFilmDao()?.insert(itemsFilms.toList())
                    callback.onSuccess(
                        Db.getInstance(App.instance.applicationContext)
                            ?.getFilmDao()?.getAll()

                    )
                }
            })
    }

    interface GetFilmCallback {
        fun onSuccess(films: List<FilmDb>?)
        fun onError(error: String)
    }

    fun insertFavourites(filmItem: FilmItem) {
        Db.getInstance(App.instance.applicationContext)
            ?.getFavouritesDao()?.insert(FavouritesDb(filmItem.filmTitle, filmItem.filmPath))
    }

    fun deleteFavourites(filmItem: FilmItem) {
        Db.getInstance(App.instance.applicationContext)
            ?.getFavouritesDao()?.delete(FavouritesDb(filmItem.filmTitle, filmItem.filmPath))
    }

    fun getFavourites(): MutableList<FilmItem>? {
        var items = mutableListOf<FilmItem>()
        Db.getInstance(App.instance.applicationContext)
            ?.getFavouritesDao()?.getAll()
            ?.forEach { items.add(FilmItem(it.filmTitle, it.filmPath, "",false,false)) }
        return items
    }

    fun updateFilm(filmItem: FilmItem) {
        var filmDb = Db.getInstance(App.instance.applicationContext)?.getFilmDao()?.findByTitle(filmItem.filmTitle)
        if (filmDb != null) {
            filmDb.isFavorite = filmItem.isFavorite
            Db.getInstance(App.instance.applicationContext)?.getFilmDao()?.update(filmDb)
        }
    }
}