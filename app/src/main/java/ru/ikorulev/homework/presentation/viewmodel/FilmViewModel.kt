package ru.ikorulev.homework.presentation.viewmodel

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import ru.ikorulev.homework.framework.WatchDateReceiver
import ru.ikorulev.homework.data.FilmItem
import ru.ikorulev.homework.data.repository.FilmRepository
import ru.ikorulev.homework.data.room.FilmDb
import ru.ikorulev.homework.data.tmdb.TMDbService
import java.util.*
import javax.inject.Inject

class FilmViewModel @Inject constructor(
    application: Application,
    val repository: FilmRepository,
    val tMDbService: TMDbService
) : AndroidViewModel(application) {

    //val context = application.applicationContext

    private val mFilms = MutableLiveData<List<FilmItem>>()
    val films: LiveData<List<FilmItem>>
        get() = mFilms

    private val mFavourites = MutableLiveData<List<FilmItem>>()
    val favourites: LiveData<List<FilmItem>>
        get() = mFavourites

    private val mSelectedFilm = MutableLiveData<FilmItem>()
    private val mErrors = MutableLiveData<String>()

    val selectedFilms: LiveData<FilmItem>
        get() = mSelectedFilm

    val errors: LiveData<String>
        get() = mErrors

    lateinit var datePickerFilmItem: FilmItem

    private val mWatchLater = MutableLiveData<List<FilmItem>>()
    val watchLater: LiveData<List<FilmItem>>
        get() = mWatchLater

    private val disposables = CompositeDisposable()

    init {


        val calendar = Calendar.getInstance()

        repository.getFilms()
            .subscribeOn(Schedulers.io())
            .map { items ->
                items.map { item ->
                    FilmItem(
                        item.filmId,
                        item.filmTitle,
                        item.filmPath,
                        item.filmDetails,
                        item.isSelected,
                        item.isFavorite,
                        item.isWatchLater,
                        watchDate = calendar.time
                    )
                }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                mFilms.value = it.toList()
            }, {
                mErrors.value = "Ошибка базы данных"
            })
            .addTo(disposables)

        repository.getFavourites()
            .subscribeOn(Schedulers.io())
            .map { items ->
                items.map { item ->
                    FilmItem(
                        item.filmId,
                        item.filmTitle,
                        item.filmPath,
                        "",
                        isSelected = false,
                        isFavorite = false,
                        isWatchLater = false,
                        watchDate = calendar.time
                    )
                }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ list ->
                mFavourites.value = list
            }, {
                mErrors.value = "Ошибка базы данных"
            })
            .addTo(disposables)

        repository.getWatchLater()
            .subscribeOn(Schedulers.io())
            .map { items ->
                items.map { item ->
                    FilmItem(
                        item.filmId,
                        item.filmTitle,
                        item.filmPath,
                        "",
                        isSelected = false,
                        isFavorite = false,
                        isWatchLater = false,
                        watchDate = item.watchDate
                    )
                }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ list ->
                mWatchLater.value = list
            }, {
                mErrors.value = "Ошибка базы данных"
            })
            .addTo(disposables)
    }

    fun initFilms() {
        repository.getListFilms()
            .subscribeOn(Schedulers.io())
            .filter {
                it.isEmpty()
            }
            .subscribe {
                loadFilms(1)
            }
            ?.addTo(disposables)
    }

    fun loadFilms(page: Int = 1) {

        tMDbService.getPopularFilms(page = page)
            .subscribeOn(Schedulers.io())
            .map { filmResults ->
                val filmDb = mutableListOf<FilmDb>()
                var filmSorting = repository.findMaxSorting()
                filmResults.movies.forEach {
                    if (it.filmId != 0
                        && it.filmTitle != null && it.filmTitle.isNotEmpty()
                        && it.filmPath != null && it.filmPath.isNotEmpty()
                        && it.filmDetails != null && it.filmDetails.isNotEmpty()
                    ) {
                        filmDb.add(
                            FilmDb(
                                it.filmId,
                                it.filmTitle,
                                it.filmPath,
                                it.filmDetails,
                                ++filmSorting
                            )
                        )
                    }
                }
                repository.insertFilms(filmDb.toList())
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({}, {
                mErrors.value = if (it is HttpException) {
                    "Ошибка сервера, код ${it.code()}"
                } else {
                    "Ошибка сети"
                }
            })
            .addTo(disposables)
    }

    fun onFilmDetailsClick(filmItem: FilmItem) {
        mSelectedFilm.value = filmItem
        repository.getListFilms()
            .subscribeOn(Schedulers.io())
            .subscribe { items ->
                items?.forEach {
                    it.isSelected = it.filmTitle == filmItem.filmTitle
                }
                if (items != null) {
                    repository.updateFilms(items)
                }
            }
            ?.addTo(disposables)
    }

    fun insertFavourites(filmItem: FilmItem) {
        filmItem.isFavorite = true

        val film = Single.just(filmItem)
        film.subscribeOn(Schedulers.io())
            .subscribe { item ->
                repository.insertFavourites(item)
                repository.updateFilm(item)
            }
            .addTo(disposables)
    }


    fun deleteFavourites(filmItem: FilmItem) {
        filmItem.isFavorite = false

        val film = Single.just(filmItem)
        film.subscribeOn(Schedulers.io())
            .subscribe { item ->
                repository.deleteFavourites(item)
                repository.updateFilm(item)
            }
            .addTo(disposables)
    }

    fun onDatePickerDialogClick(watchDate: Date?) {
        if (watchDate != null) {
            datePickerFilmItem.isWatchLater = true
            datePickerFilmItem.watchDate = watchDate

            val film = Single.just(datePickerFilmItem)
            film.subscribeOn(Schedulers.io())
                .subscribe { item ->
                    repository.updateFilm(item)
                    repository.insertWatchLater(item)
                    startNotification(item, getApplication())
                }
                .addTo(disposables)
        }
    }

    private fun startNotification(film: FilmItem, context: Context) {
        val am = ContextCompat.getSystemService(context, AlarmManager::class.java)
        val intent = Intent(
            film.filmId.toString(),
            null,
            context,
            WatchDateReceiver::class.java
        )
            .putExtra("filmTitle", film.filmTitle)
            .putExtra("filmPath", film.filmPath)
        val pIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_ONE_SHOT
        )
        am?.set(AlarmManager.RTC_WAKEUP, film.watchDate.time, pIntent)
    }

    fun deleteWatchLater(filmItem: FilmItem) {
        filmItem.isWatchLater = false

        val film = Single.just(filmItem)
        film.subscribeOn(Schedulers.io())
            .subscribe { item ->
                repository.deleteWatchLater(item)
                repository.updateFilm(item)
            }
            .addTo(disposables)
    }

    fun clearError() {
        mErrors.value = ""
    }

    fun clearTables() {
        val clear = Single.just(true)
        clear.subscribeOn(Schedulers.io())
            .subscribe { _ ->
                repository.clearTables()
            }
            .addTo(disposables)
    }

    fun selectFilm(title: String) {
        val itemTitle = Single.just(title)

        itemTitle.subscribeOn(Schedulers.io())
            .map {
                repository.findFilmByTitle(it)
            }
            .filter {
                it != null
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ item ->
                mSelectedFilm.value = item
            }, {
                mErrors.value = "Фильм не найден"
            })
            .addTo(disposables)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

}




