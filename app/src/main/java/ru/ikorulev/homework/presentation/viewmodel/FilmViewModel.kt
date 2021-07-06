package ru.ikorulev.homework.presentation.viewmodel


import android.app.Application
import androidx.lifecycle.*
import ru.ikorulev.homework.App
import ru.ikorulev.homework.data.FilmDb
import ru.ikorulev.homework.data.FilmItem
import ru.ikorulev.homework.domain.Interactor

class FilmViewModel(application: Application)  : AndroidViewModel(application){

    private val interactor = App.instance.interactor

    private val mFilms = MutableLiveData<List<FilmItem>>()
    private val mFavourites = MutableLiveData<MutableList<FilmItem>>()
    private val mError = MutableLiveData<String>()

    private val filmItems = mutableListOf<FilmItem>()

    val films: LiveData<List<FilmItem>> = mFilms
    val favourites: LiveData<MutableList<FilmItem>> = mFavourites

    val error: LiveData<String> = mError

    //val films = mutableListOf<FilmItem>()
    //val favourites = mutableListOf<FilmItem>()

    fun loadFilms(page: Int = 1){
        interactor.loadFilms(page, object : Interactor.GetFilmCallback {
            override fun onSuccess(filmsDb: List<FilmDb>?) {
                filmItems.clear()
                filmsDb?.forEach{
                    filmItems.add(
                        FilmItem(
                            //it.id,
                            it.filmTitle,
                            it.filmPath,
                            it.filmDetails,
                            false,
                            false
                        ))}
                mFilms.value = filmItems
            }

            override fun onError(error: String) {
                mError.value = error
            }
        })
    }

    fun onFilmClick(filmItem: FilmItem) {
        mFilms.value?.forEach {
            it.isSelected = it == filmItem
        }
    }

    fun onFavoriteClick(filmItem: FilmItem): Boolean {
        if (mFavourites.value == null) {
            mFavourites.value = mutableListOf<FilmItem>()
        }
        return if (!mFavourites.value!!.contains(filmItem)) {
            interactor.insertFavourites(filmItem)
            mFavourites.value=interactor.getFavourites()

            filmItem.isFavorite = true
            interactor.updateFilm(filmItem)
            true
        } else {
            interactor.deleteFavourites(filmItem)
            mFavourites.value!!.remove(filmItem)
            filmItem.isFavorite = false
            interactor.updateFilm(filmItem)
            false
        }
    }
}