package ru.ikorulev.homework.presentation.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.ikorulev.homework.App
import ru.ikorulev.homework.data.FilmItem
import ru.ikorulev.homework.domain.Interactor

class FilmViewModel  : ViewModel(){

    private val interactor = App.instance.interactor

    private val mFilms = MutableLiveData<List<FilmItem>>()
    private val mFavourites = MutableLiveData<MutableList<FilmItem>>()
    private val mError = MutableLiveData<String>()

    val films: LiveData<List<FilmItem>> = mFilms
    val favourites: LiveData<MutableList<FilmItem>> = mFavourites

    val error: LiveData<String> = mError

    //val films = mutableListOf<FilmItem>()
    //val favourites = mutableListOf<FilmItem>()

    fun getFilms(){
        interactor.getFilms(object : Interactor.GetFilmCallback {
            override fun onSuccess(films: List<FilmItem>) {
                mFilms.value = films
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
            mFavourites.value!!.add(filmItem)
            filmItem.isFavorite = true
            true
        } else {
            mFavourites.value!!.remove(filmItem)
            filmItem.isFavorite = false
            false
        }
    }
}