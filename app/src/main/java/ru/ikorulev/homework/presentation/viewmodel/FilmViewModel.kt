package ru.ikorulev.homework.presentation.viewmodel


import android.app.Application
import androidx.lifecycle.*
import ru.ikorulev.homework.App
import ru.ikorulev.homework.data.room.FilmDb
import ru.ikorulev.homework.data.FilmItem
import ru.ikorulev.homework.data.room.Db
import ru.ikorulev.homework.data.room.FavouritesDb
import ru.ikorulev.homework.data.room.DataRepository
import ru.ikorulev.homework.domain.Interactor

class FilmViewModel(application: Application)  : AndroidViewModel(application){

    private val interactor = App.instance.interactor
    private val repository: DataRepository

    private val mSelectedFilm = MutableLiveData<FilmItem>()
    private val mErrors = MutableLiveData<String>()

    val films: LiveData<List<FilmDb>>?
    val favourites: LiveData<List<FavouritesDb>>?

    val selectedFilms: LiveData<FilmItem>
        get() = mSelectedFilm

    val errors: LiveData<String>
        get() = mErrors


    init {
        val filmDao = Db.getInstance(App.instance.applicationContext)?.getFilmDao()
        val favouritesDao = Db.getInstance(App.instance.applicationContext)?.getFavouritesDao()
        repository = DataRepository(filmDao, favouritesDao)
        films = repository.films
        favourites = repository.favourites
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

    fun indexOfFilm(filmItem: FilmItem): Int {
        val filmDb = interactor.findFilmDb(filmItem)
        if (filmDb!=null) {
            return films?.value!!.indexOf(filmDb)
        } else{
            return 0
        }
    }

    fun clearError(){
        mErrors.value = ""
    }

}