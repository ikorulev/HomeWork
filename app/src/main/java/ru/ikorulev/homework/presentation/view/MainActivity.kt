package ru.ikorulev.homework.presentation.view

import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.ikorulev.homework.R
import ru.ikorulev.homework.data.FilmItem
import ru.ikorulev.homework.data.repository.FilmRepository
import ru.ikorulev.homework.data.tmdb.TMDbService
import ru.ikorulev.homework.di.AppModule
import ru.ikorulev.homework.di.DaggerAppComponent
import ru.ikorulev.homework.di.RoomModule
import ru.ikorulev.homework.presentation.view.favourites.FavouritesFragment
import ru.ikorulev.homework.presentation.view.watch_later.WatchLaterFragment
import ru.ikorulev.homework.presentation.viewmodel.FilmViewModel
import ru.ikorulev.homework.presentation.viewmodel.FilmViewModelFactory
import javax.inject.Inject


class MainActivity : AppCompatActivity(), FilmListFragment.OnFilmDetailsClickListener {

    companion object {
        var navItem = R.id.nav_list
    }

    private lateinit var bottomNavigation: BottomNavigationView

    private lateinit var viewModel: FilmViewModel
    private lateinit var viewModelFactory: FilmViewModelFactory
    //private val viewModel: FilmViewModel by viewModels()

    @Inject
    lateinit var repository: FilmRepository

    @Inject
    lateinit var tMDbService: TMDbService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DaggerAppComponent.builder()
            .appModule(AppModule(application))
            .roomModule(RoomModule(application))
            .build()
            .inject(this)

        initBottomNavigation()
        viewModelFactory = FilmViewModelFactory(application, repository, tMDbService)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(FilmViewModel::class.java)

        viewModel.initFilms()

        val title = intent.getStringExtra("filmTitle")
        if (title.isNullOrEmpty()) {
            openFragment()
        } else {
            viewModel.selectFilm(title)
            supportActionBar?.title = title
            openFilmDetails()
        }
    }

    private fun initBottomNavigation() {
        bottomNavigation = findViewById(R.id.filmNavigation)
        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            navItem = item.itemId
            //при переходе в начало очищаем стек
            if (navItem == R.id.nav_list) {
                supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            }
            openFragment()
            true
        }
    }

    private fun openFragment() {
        when (navItem) {
            R.id.nav_list -> {
                supportActionBar?.title = getString(R.string.app_name)
                openFilmList()
            }
            R.id.nav_favourites -> {
                supportActionBar?.title = getString(R.string.favourites)
                openFavourites()
            }
            R.id.nav_watch_later -> {
                supportActionBar?.title = getString(R.string.watch_later)
                openWatchLater()
            }
            R.id.nav_download -> {
                viewModel.loadFilms()
            }
            R.id.nav_clear -> {
                viewModel.clearTables()
            }
        }
    }

    private fun openFilmList() {
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.fragmentContainer,
                FilmListFragment(),
                FilmListFragment.TAG
            )
            .commit()
    }

    private fun openFilmDetails() {
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.fragmentContainer,
                FilmDetailsFragment(),
                FilmDetailsFragment.TAG
            )
            .addToBackStack("FilmDetails")
            .commit()
    }

    private fun openFavourites() {
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.fragmentContainer,
                FavouritesFragment(),
                FavouritesFragment.TAG
            )
            .addToBackStack("FilmFavourites")
            .commit()
    }

    private fun openWatchLater() {
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.fragmentContainer,
                WatchLaterFragment(),
                WatchLaterFragment.TAG
            )
            .addToBackStack("WatchList")
            .commit()
    }

    override fun onFilmDetailsClick(filmItem: FilmItem) {
        supportActionBar?.title = filmItem.filmTitle
        openFilmDetails()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportActionBar?.title = getString(R.string.app_name)
            if (navItem != R.id.nav_list) {
                supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                navItem = R.id.nav_list
                bottomNavigation.selectedItemId = navItem
                openFragment()
            } else {
                supportFragmentManager.popBackStack()
            }
        } else {
            AlertDialog.Builder(this).apply {
                setTitle("Подтверждение")
                setMessage("Вы уверены, что хотите выйти из программы?")

                setPositiveButton("Да") { dialogInterface: DialogInterface, i: Int ->
                    super.onBackPressed()
                }
                setNegativeButton("Нет") { dialogInterface: DialogInterface, i: Int -> }

                setCancelable(true)
            }.create().show()
        }
    }
}