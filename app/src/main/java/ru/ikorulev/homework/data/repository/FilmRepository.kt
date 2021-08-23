package ru.ikorulev.homework.data.repository

import io.reactivex.Flowable
import io.reactivex.Single
import ru.ikorulev.homework.data.FilmItem
import ru.ikorulev.homework.data.room.FavouritesDb
import ru.ikorulev.homework.data.room.FilmDb
import ru.ikorulev.homework.data.room.WatchLaterDb

interface FilmRepository {

    fun clearTables()

    //Films
    fun getFilms(): Flowable<List<FilmDb>?>
    fun getListFilms(): Single<List<FilmDb>?>
    fun updateFilms(items: List<FilmDb>)
    fun insertFilms(filmDb: List<FilmDb>)
    fun findFilmByTitle(title: String): FilmItem?
    fun updateFilm(filmItem: FilmItem)

    //Favourites
    fun getFavourites(): Flowable<List<FavouritesDb>?>
    fun insertFavourites(filmItem: FilmItem)
    fun deleteFavourites(filmItem: FilmItem)

    //WatchLater
    fun getWatchLater(): Flowable<List<WatchLaterDb>?>
    fun insertWatchLater(filmItem: FilmItem)
    fun deleteWatchLater(filmItem: FilmItem)

}