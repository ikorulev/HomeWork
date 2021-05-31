package ru.ikorulev.homework

import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.ikorulev.homework.Favourites.FavouritesFragment
import kotlin.math.max

class MainActivity : AppCompatActivity(), FilmListFragment.OnFilmDetailClickListener {
    companion object {
        private const val FILM_NUMBER = "FILM_NUMBER"
        var filmNumber = -1
        var navItem = R.id.nav_list
    }

    private lateinit var BottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initBottomNavigation()
        initFilms()
        openFragmet()
    }

    private fun initBottomNavigation() {
        BottomNavigation = findViewById(R.id.filmNavigation)
        BottomNavigation.setOnNavigationItemReselectedListener { false }
        BottomNavigation.setOnNavigationItemSelectedListener {item ->
            navItem = item.itemId

            while (supportFragmentManager.backStackEntryCount > 0){
                supportFragmentManager.popBackStackImmediate()
            }

            openFragmet()
            true
        }

    }

    private fun openFragmet() {
        when (navItem) {
            R.id.nav_list -> {
                openFilmList()
            }
            R.id.nav_detail -> {
                openFilmDetail()
            }
            R.id.nav_favourites -> {
                openFavourites()
            }
        }
    }

    private fun openFilmList() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer,  FilmListFragment(), FilmListFragment.TAG)
            .commit()
    }

    private fun openFilmDetail() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, FilmDetailFragment.newInstance(max(filmNumber,0)), FilmDetailFragment.TAG)
            //.addToBackStack("FilmDetail")
            .commit()
    }

    private fun openFavourites() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer,  FavouritesFragment(), FavouritesFragment.TAG)
            .commit()
    }

    override fun onFilmDescriptionClick(filmItem: FilmItem) {
        DataRepository.films.forEach {
            if (it == filmItem) {it.isSelected = true}
            else {it.isSelected = false}
        }

        filmNumber = DataRepository.films.indexOf(filmItem)
        navItem = R.id.nav_detail
        BottomNavigation.selectedItemId = R.id.nav_detail

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, FilmDetailFragment.newInstance(filmNumber), FilmDetailFragment.TAG)
            .addToBackStack("FilmDescription")
            .commit()
    }

    private fun initFilms() {
        if (DataRepository.films.isEmpty()) {
            DataRepository.films.add(FilmItem(getString(R.string.title1), getString(R.string.detail1), R.drawable.img1,false, false))
            DataRepository.films.add(FilmItem( getString(R.string.title2), getString(R.string.detail2), R.drawable.img2,false, false))
            DataRepository.films.add(FilmItem( getString(R.string.title3), getString(R.string.detail3), R.drawable.img3,false, false))
            DataRepository.films.add(FilmItem( getString(R.string.title4), getString(R.string.detail4), R.drawable.img4,false, false))
        }
    }


    override fun onBackPressed() {

        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
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