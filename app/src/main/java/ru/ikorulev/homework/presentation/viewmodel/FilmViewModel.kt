package ru.ikorulev.homework.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.ikorulev.homework.App
import ru.ikorulev.homework.data.FilmItem
import ru.ikorulev.homework.data.room.DataRepository
import java.util.*

class FilmViewModel(application: Application) : AndroidViewModel(application) {

    private val interactor = App.instance.interactor
    private val repository: DataRepository = DataRepository()

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

    init {

        val calendar = Calendar.getInstance()

        viewModelScope.launch(Dispatchers.IO) {
            repository.getFilms()?.collect { list ->
                val items = mutableListOf<FilmItem>()
                list.forEach {
                    items.add(
                        FilmItem(
                            it.filmId,
                            it.filmTitle,
                            it.filmPath,
                            it.filmDetails,
                            it.isSelected,
                            it.isFavorite,
                            it.isWatchLater,
                            calendar.time,
                        )
                    )
                }
                withContext(Dispatchers.Main) {
                    mFilms.value = items
                }
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            repository.getFavourites()?.collect { list ->
                val items = mutableListOf<FilmItem>()
                list.forEach {
                    items.add(
                        FilmItem(
                            it.filmId,
                            it.filmTitle,
                            it.filmPath,
                            "",
                            isSelected = false,
                            isFavorite = false,
                            isWatchLater = false,
                            watchDate = calendar.time,
                        )
                    )
                }
                withContext(Dispatchers.Main) {
                    mFavourites.value = items
                }
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            repository.getWatchLater()?.collect { list ->
                val items = mutableListOf<FilmItem>()
                list.forEach {
                    items.add(
                        FilmItem(
                            it.filmId,
                            it.filmTitle,
                            it.filmPath,
                            "",
                            isSelected = false,
                            isFavorite = false,
                            isWatchLater = false,
                            watchDate = it.watchDate,
                        )
                    )
                }
                withContext(Dispatchers.Main) {
                    mWatchLater.value = items
                }
            }
        }
    }

    fun initFilms() {
        viewModelScope.launch(Dispatchers.IO) {
            if (repository.isEmpty()) {
                this@FilmViewModel.loadFilms(1)
            }
        }
    }

    fun loadFilms(page: Int = 1) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                interactor.loadFilms(page)
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    mErrors.value = if (e is HttpException) {
                        "Ошибка сервера, код ${e.code()}"
                    } else {
                        "Ошибка сети"
                    }
                }
            }
        }
    }

    fun onFilmClick(filmItem: FilmItem) {
        mSelectedFilm.value = filmItem
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateFilmIsSelected(filmItem)
        }
    }

    fun insertFavourites(filmItem: FilmItem) {
        filmItem.isFavorite = true
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertFavourites(filmItem)
            repository.updateFilmIsFavorite(filmItem)
        }
    }

    fun deleteFavourites(filmItem: FilmItem) {
        filmItem.isFavorite = false
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFavourites(filmItem)
            repository.updateFilmIsFavorite(filmItem)
        }
    }

    suspend fun onDatePickerDialogClick(watchDate: Date?) {
        if (watchDate != null) {
            datePickerFilmItem.isWatchLater = true
            datePickerFilmItem.watchDate = watchDate
            repository.updateFilmIsWatchLater(datePickerFilmItem)
            repository.insertWatchLater(datePickerFilmItem)
            interactor.startNotification(App.instance.applicationContext, datePickerFilmItem)
        }
    }

    fun deleteWatchLater(filmItem: FilmItem) {
        filmItem.isWatchLater = false
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteWatchLater(filmItem)
            repository.updateFilmIsWatchLater(filmItem)
        }
    }

    fun clearError() {
        mErrors.value = ""
    }

    fun clearTables() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.clearTables()
        }
    }

    fun selectFilm(title: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val item = repository.findFilm(title)
            if (item != null) {
                withContext(Dispatchers.Main) {
                    mSelectedFilm.value = item!!
                }
            }
        }
    }

}


