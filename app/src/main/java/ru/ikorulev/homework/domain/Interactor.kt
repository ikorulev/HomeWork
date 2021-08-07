package ru.ikorulev.homework.domain

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.getSystemService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.ikorulev.homework.WatchDateReceiver
import ru.ikorulev.homework.data.FilmItem
import ru.ikorulev.homework.data.room.DataRepository
import ru.ikorulev.homework.data.room.FilmDb
import ru.ikorulev.homework.data.tmdb.TMDbService
import java.util.*

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

    fun startNotification(context: Context, film: FilmItem) {
        val am = getSystemService(context, AlarmManager::class.java)
        val intent = Intent(film.filmId.toString(), null, context, WatchDateReceiver::class.java)
            .putExtra("filmTitle", film.filmTitle)
            .putExtra("filmPath", film.filmPath)
        val pIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        am?.set(AlarmManager.RTC_WAKEUP, film.watchDate.time, pIntent)
    }
}