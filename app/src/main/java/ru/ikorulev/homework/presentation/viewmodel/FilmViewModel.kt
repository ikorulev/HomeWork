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
import ru.ikorulev.homework.App
import ru.ikorulev.homework.data.FilmItem
import ru.ikorulev.homework.data.room.DataRepository
import ru.ikorulev.homework.domain.Interactor
import java.util.*

class FilmViewModel(application: Application) : AndroidViewModel(application) {

    private val interactor = App.instance.interactor
    private val repository: DataRepository

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

    lateinit var datePickerFilmItem:  FilmItem

    private val mWatchLater = MutableLiveData<List<FilmItem>>()
    val watchLater: LiveData<List<FilmItem>>
        get() = mWatchLater

    init {

        repository = DataRepository()
        val calendar = Calendar.getInstance()

        viewModelScope.launch(Dispatchers.IO) {
            repository.getFilms()?.collect() { list ->
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
            repository.getFavourites()?.collect() { list ->
                val items = mutableListOf<FilmItem>()
                list.forEach {
                    items.add(
                        FilmItem(
                            it.filmId,
                            it.filmTitle,
                            it.filmPath,
                            "",
                            false,
                            false,
                            false,
                            calendar.time,
                        )
                    )
                }
                withContext(Dispatchers.Main) {
                    mFavourites.value = items
                }
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            repository.getWatchLater()?.collect() {list ->
                val items = mutableListOf<FilmItem>()
                list.forEach {
                    items.add(
                        FilmItem(
                            it.filmId,
                            it.filmTitle,
                            it.filmPath,
                            "",
                            false,
                            false,
                            false,
                            it.watchDate,
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
        if (interactor.isEmpty()) {
            loadFilms(1)
        }
    }

    fun loadFilms(page: Int = 1) {
        interactor.loadFilms(page, object : Interactor.GetFilmCallback {
            override fun onError(error: String) {
                mErrors.value = error
            }
        })
    }


    fun onFilmClick(filmItem: FilmItem) {
        mSelectedFilm.value = filmItem
        interactor.selectFilm(filmItem)
    }

    fun onFavoriteClick(filmItem: FilmItem): Boolean {
        return if (interactor.findFavourites(filmItem) == false) {
            interactor.insertFavourites(filmItem)
            filmItem.isFavorite = true
            interactor.updateFilmIsFavorite(filmItem)
            true
        } else {
            interactor.deleteFavourites(filmItem)
            filmItem.isFavorite = false
            interactor.updateFilmIsFavorite(filmItem)
            false
        }
    }

    fun deleteWatchLater(filmItem: FilmItem) {
        viewModelScope.launch(Dispatchers.IO) {
            interactor.deleteWatchLater(filmItem)
            filmItem.isWatchLater = false
            interactor.updateFilmIsWatchLater(filmItem)
        }
    }

    suspend fun onDatePickerDialogClick(watchDate: Date?) {
        if (watchDate!=null) {
            datePickerFilmItem.isWatchLater = true
            datePickerFilmItem.watchDate=watchDate
            interactor.updateFilmIsWatchLater(datePickerFilmItem)
            interactor.insertWatchLater(datePickerFilmItem)
        }
    }

    fun clearError() {
        mErrors.value = ""
    }

}


