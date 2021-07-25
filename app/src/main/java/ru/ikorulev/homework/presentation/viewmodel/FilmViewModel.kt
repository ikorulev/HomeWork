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

    private val mFilms = MutableLiveData<List<FilmItem>>()
    val filmItems: LiveData<List<FilmItem>>
        get() = mFilms

    private val mFavourites = MutableLiveData<List<FilmItem>>()
    val favouriteItems: LiveData<List<FilmItem>>
        get() = mFavourites


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

    fun loadFilmItems(filmsDb: List<FilmDb>) {

        val items = mutableListOf<FilmItem>()
        filmsDb.forEach {
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

    fun loadFavouriteItems(favouritesDb: List<FavouritesDb>) {

        val items = mutableListOf<FilmItem>()
        favouritesDb.forEach {
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