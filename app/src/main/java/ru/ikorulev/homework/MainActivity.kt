package ru.ikorulev.homework

import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.ikorulev.homework.Favourites.FavouritesFragment

class MainActivity : AppCompatActivity(), FilmListFragment.OnFilmDetailsClickListener {
    companion object {
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
        BottomNavigation.setOnNavigationItemReselectedListener { true }
        BottomNavigation.setOnNavigationItemSelectedListener { item ->
            navItem = item.itemId
            //при переходе в начало очищаем стек
            if (navItem == R.id.nav_list) {
                supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
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
                openFilmDetails()
            }
            R.id.nav_favourites -> {
                openFavourites()
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
                FavouritesFragment.TAG)
            .addToBackStack("FilmFavourites")
            .commit()
    }

    override fun onFilmDetailsClick(filmItem: FilmItem) {
        DataRepository.films.forEach {
            it.isSelected = it == filmItem
        }

        navItem = R.id.nav_detail
        BottomNavigation.selectedItemId = navItem
        openFilmDetails()

    }

    private fun initFilms() {
        if (DataRepository.films.isEmpty()) {
            DataRepository.films.add(
                FilmItem(
                    getString(R.string.title1),
                    getString(R.string.detail1),
                    R.drawable.img1,
                    false,
                    false
                )
            )
            DataRepository.films.add(
                FilmItem(
                    getString(R.string.title2),
                    getString(R.string.detail2),
                    R.drawable.img2,
                    false,
                    false
                )
            )
            DataRepository.films.add(
                FilmItem(
                    getString(R.string.title3),
                    getString(R.string.detail3),
                    R.drawable.img3,
                    false,
                    false
                )
            )
            DataRepository.films.add(
                FilmItem(
                    getString(R.string.title4),
                    getString(R.string.detail4),
                    R.drawable.img4,
                    false,
                    false
                )
            )
        }
    }


    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            if (navItem != R.id.nav_list) {
                supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                navItem = R.id.nav_list
                BottomNavigation.selectedItemId = navItem
                openFragmet()
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
