package ru.ikorulev.homework.domain

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.ikorulev.homework.App
import ru.ikorulev.homework.data.*
import ru.ikorulev.homework.data.room.DataRepository
import ru.ikorulev.homework.data.room.Db
import ru.ikorulev.homework.data.room.FavouritesDb
import ru.ikorulev.homework.data.room.FilmDb
import ru.ikorulev.homework.data.tmdb.GetFilmsResults
import ru.ikorulev.homework.data.tmdb.TMDbService
import java.io.ByteArrayOutputStream

class Interactor(

    private val tmDbService: TMDbService

) {
    //Films
    fun deleteFilms(){
        Db.getInstance(App.instance.applicationContext)?.getFilmDao()?.deleteAll()
    }

    fun loadFilms(page: Int) {
        val filmDb = mutableListOf<FilmDb>()
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
                    filmDb.clear()
                    if (response.isSuccessful) {
                        response.body()?.movies?.forEach {
                            if (it.filmTitle != null && it.filmTitle.isNotEmpty()
                                && it.filmPath != null && it.filmPath.isNotEmpty()
                                && it.filmDetails != null && it.filmDetails.isNotEmpty()
                            ) {
                                filmDb.add(
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
                        ?.getFilmDao()?.insert(filmDb.toList())
                }
            })
    }

    fun updateFilmIsFavorite(filmItem: FilmItem) {
        val filmDb = Db.getInstance(App.instance.applicationContext)?.getFilmDao()?.findByTitle(filmItem.filmTitle)
        if (filmDb != null) {
            filmDb.isFavorite = filmItem.isFavorite
            Db.getInstance(App.instance.applicationContext)?.getFilmDao()?.update(filmDb)
        }
    }

    fun selectFilm(filmItem: FilmItem, filmDb: List<FilmDb>?) {
        if (filmDb!=null) {
            filmDb.forEach {
                it.isSelected = it.filmTitle == filmItem.filmTitle
            }
            Db.getInstance(App.instance.applicationContext)?.getFilmDao()?.updateAll(filmDb.toList())
        }
    }

    //Favourites
    fun findFavourites(filmItem: FilmItem) : Boolean {
        val favouritesDb = Db.getInstance(App.instance.applicationContext)?.getFavouritesDao()?.findByTitle(filmItem.filmTitle)
        return favouritesDb != null
    }

    fun insertFavourites(filmItem: FilmItem) {
        Db.getInstance(App.instance.applicationContext)
            ?.getFavouritesDao()?.insert(FavouritesDb(filmItem.filmTitle, filmItem.filmPath))
    }

    fun deleteFavourites(filmItem: FilmItem) {
        val favouritesDb = Db.getInstance(App.instance.applicationContext)?.getFavouritesDao()?.findByTitle(filmItem.filmTitle)
        if (favouritesDb!=null) {
            Db.getInstance(App.instance.applicationContext)?.getFavouritesDao()?.delete(favouritesDb)
        }
    }




}