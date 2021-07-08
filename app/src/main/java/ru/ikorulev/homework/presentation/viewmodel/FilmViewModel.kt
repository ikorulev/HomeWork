package ru.ikorulev.homework.presentation.viewmodel


import android.app.Application
import androidx.lifecycle.*
import ru.ikorulev.homework.App
import ru.ikorulev.homework.data.room.FilmDb
import ru.ikorulev.homework.data.FilmItem
import ru.ikorulev.homework.data.room.Db
import ru.ikorulev.homework.data.room.FavouritesDb
import ru.ikorulev.homework.data.room.DataRepository

class FilmViewModel(application: Application)  : AndroidViewModel(application){

    private val interactor = App.instance.interactor
    private val repository: DataRepository

    private val mError = MutableLiveData<String>()

    val error: LiveData<String> = mError

    val films: LiveData<List<FilmDb>>?
    val favourites: LiveData<List<FavouritesDb>>?

    init {
        val filmDao = Db.getInstance(App.instance.applicationContext)?.getFilmDao()
        val favouritesDao = Db.getInstance(App.instance.applicationContext)?.getFavouritesDao()
        repository = DataRepository(filmDao, favouritesDao)
        films = repository.films
        favourites = repository.favourites
    }

    fun loadFilms(page: Int = 1){
        interactor.loadFilms(page)
    }

    fun onFilmClick(filmItem: FilmItem) {
        interactor.selectFilm(filmItem, films?.value)
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
}