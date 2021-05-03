package ru.ikorulev.homework

import android.content.DialogInterface
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    companion object {
        private const val BUTTON_NUMBER = "BUTTON_NUMBER"
        var btnNumber = 0
     }

    private val recyclerFilm by lazy { findViewById<RecyclerView>(R.id.recyclerFilm) }
    private val films = mutableListOf<FilmItem>()
    private val favourites = mutableListOf<FilmItem>()
    private val btnFavourites by lazy { findViewById<Button>(R.id.btnFavourites) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initFilms()
        initRecycler()

        btnFavourites.setOnClickListener() {

            val intent = Intent(this, FilmFavourites::class.java)
            intent.putParcelableArrayListExtra(FilmFavourites.LIST_FAVOURITES, favourites as ArrayList<FilmItem>)
            startActivity(intent)
        }

    }

    private fun initFilms() {
        films.add(FilmItem(1, getString(R.string.title1),R.drawable.img1,false))
        films.add(FilmItem(2, getString(R.string.title2),R.drawable.img2,false))
        films.add(FilmItem(3, getString(R.string.title3),R.drawable.img3,false))
        films.add(FilmItem(4, getString(R.string.title4),R.drawable.img4,false))
    }

    private fun initRecycler() {
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
            val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            recyclerFilm.layoutManager = layoutManager

        } else {
            val layoutManager = GridLayoutManager(this, 2)
            recyclerFilm.layoutManager = layoutManager
        }

        recyclerFilm.adapter = FilmAdapter(films, object : FilmAdapter.FilmClickListener {
            override fun onFilmClick(filmItem: FilmItem){

                btnNumber = filmItem.filmNumber
                recyclerFilm.adapter?.notifyDataSetChanged()

                val intent = Intent(applicationContext, FilmDescription::class.java)

                intent.putExtra(FilmDescription.BUTTON_NUMBER, filmItem.filmNumber)
                startActivity(intent)
            }

            override fun onFavoriteClick(filmItem: FilmItem){
                if (!favourites.contains(filmItem)) {
                    favourites.add(filmItem)
                    filmItem.filmFavorite = true
                    recyclerFilm.adapter?.notifyItemChanged(filmItem.filmNumber-1)
                } else {
                    favourites.remove(filmItem)
                    filmItem.filmFavorite = false
                    recyclerFilm.adapter?.notifyItemChanged(filmItem.filmNumber-1)
                }
            }
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(BUTTON_NUMBER, btnNumber)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        savedInstanceState.let {
            btnNumber = it.getInt(BUTTON_NUMBER)
        }
    }

    override fun onBackPressed() {
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