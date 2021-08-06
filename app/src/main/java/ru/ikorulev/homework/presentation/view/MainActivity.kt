package ru.ikorulev.homework.presentation.view

import android.content.DialogInterface
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.ikorulev.homework.App
import ru.ikorulev.homework.R
import ru.ikorulev.homework.data.FilmItem
import ru.ikorulev.homework.presentation.view.favourites.FavouritesFragment
import ru.ikorulev.homework.presentation.view.watch_later.WatchLaterFragment
import ru.ikorulev.homework.presentation.viewmodel.FilmViewModel


class MainActivity : AppCompatActivity(), FilmListFragment.OnFilmDetailsClickListener {
    companion object {
        var navItem = R.id.nav_list
    }

    private lateinit var bottomNavigation: BottomNavigationView

    private val viewModel: FilmViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initBottomNavigation()
        viewModel.initFilms()
        openFragment()
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
                openFilmList()
            }
            R.id.nav_favourites -> {
                openFavourites()
            }
            R.id.nav_watch_later -> {
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
        openFilmDetails()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
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