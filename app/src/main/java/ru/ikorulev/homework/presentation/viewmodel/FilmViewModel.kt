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
import ru.ikorulev.homework.data.room.FavouritesDb
import ru.ikorulev.homework.data.room.FilmDb
import ru.ikorulev.homework.domain.Interactor

class FilmViewModel(application: Application)  : AndroidViewModel(application){

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


    init {

        repository = DataRepository()

        viewModelScope.launch(Dispatchers.IO) {
            repository.getFilms()?.collect() {list ->
                withContext(Dispatchers.Main) {
                    val items = mutableListOf<FilmItem>()
                    list.forEach {
                        items.add(
                            FilmItem(
                                it.filmTitle,
                                it.filmPath,
                                it.filmDetails,
                                it.isSelected,
                                it.isFavorite
                            )
                        )
                    }
                    mFilms.value = items
                }
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            repository.getFavourites()?.collect() {list ->
                withContext(Dispatchers.Main) {
                    val items = mutableListOf<FilmItem>()
                    list.forEach {
                        items.add(
                            FilmItem(
                                it.filmTitle,
                                it.filmPath,
                                "",
                                false,
                                false
                            )
                        )
                    }
                    mFavourites.value = items
                }
            }
        }

    }

    fun initFilms(){
        if (interactor.isEmpty()) {
            loadFilms(1)
        }
    }

    fun loadFilms(page: Int = 1){
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

    fun indexOfFilm(filmItem: FilmItem): Int {
        val items = mFilms.value?.toList()
        if (filmItem!=null && items!=null) {
            return items.indexOf(filmItem)
        } else{
            return 0
        }
    }

    fun clearError(){
        mErrors.value = ""
    }

}


