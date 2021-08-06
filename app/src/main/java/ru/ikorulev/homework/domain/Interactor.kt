package ru.ikorulev.homework.domain

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.ikorulev.homework.App
import ru.ikorulev.homework.R
import ru.ikorulev.homework.data.FilmItem
import ru.ikorulev.homework.data.room.DataRepository
import ru.ikorulev.homework.data.room.Db
import ru.ikorulev.homework.data.room.FavouritesDb
import ru.ikorulev.homework.data.room.FilmDb
import ru.ikorulev.homework.data.tmdb.GetFilmsResults
import ru.ikorulev.homework.data.tmdb.TMDbService

class Interactor(

    private val tmDbService: TMDbService,
    private val dataRepository: DataRepository

) {
    suspend fun loadFilms(page: Int) {
        return withContext(Dispatchers.IO) {
            val filmDb = mutableListOf<FilmDb>()
            tmDbService.getPopularFilms(page = page).let { filmResults ->
                filmResults.movies.forEach {
                    if (it.filmId != null && it.filmId != 0
                        && it.filmTitle != null && it.filmTitle.isNotEmpty()
                        && it.filmPath != null && it.filmPath.isNotEmpty()
                        && it.filmDetails != null && it.filmDetails.isNotEmpty()
                    ) {
                        filmDb.add(
                            FilmDb(
                                it.filmId,
                                it.filmTitle,
                                it.filmPath,
                                it.filmDetails
                            )
                        )
                    }
                }
                dataRepository.insertFilmDb(filmDb.toList())
            }
        }
    }

}