package ru.ikorulev.homework

import android.content.DialogInterface
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private val recyclerFilm by lazy { findViewById<RecyclerView>(R.id.recyclerFilm) }
    private val btnFavourites by lazy { findViewById<Button>(R.id.btnFavourites) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initFilms()
        initRecycler()

        btnFavourites.setOnClickListener() {

            val intent = Intent(this, FilmFavourites::class.java)
            intent.putParcelableArrayListExtra(FilmFavourites.LIST_FAVOURITES, DataRepository.favourites as ArrayList<FilmItem>)
            startActivity(intent)
        }

    }

    private fun initFilms() {
        if (DataRepository.films.isEmpty()){
            DataRepository.films.add(FilmItem(getString(R.string.title1), getString(R.string.detail1), R.drawable.img1,false,false))
            DataRepository.films.add(FilmItem(getString(R.string.title2), getString(R.string.detail2), R.drawable.img2,false,false))
            DataRepository.films.add(FilmItem(getString(R.string.title3), getString(R.string.detail3), R.drawable.img3,false,false))
            DataRepository.films.add(FilmItem(getString(R.string.title4), getString(R.string.detail4), R.drawable.img4,false,false))}
    }

    private fun initRecycler() {
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
            val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            recyclerFilm.layoutManager = layoutManager

        } else {
            val layoutManager = GridLayoutManager(this, 2)
            recyclerFilm.layoutManager = layoutManager
        }

        recyclerFilm.adapter = FilmAdapter(DataRepository.films, object : FilmAdapter.FilmClickListener {
            override fun onFilmClick(filmItem: FilmItem){

                //сначала у всех сбросить, чтоб выбран был только один
                DataRepository.films.forEach{  it.isSelected = false }
                filmItem.isSelected = true

                recyclerFilm.adapter?.notifyDataSetChanged()

                val intent = Intent(applicationContext, FilmDescription::class.java)

                intent.putExtra(FilmDescription.FILM_NUMBER, DataRepository.films.indexOf(filmItem))
                startActivity(intent)
            }

            override fun onFavoriteClick(filmItem: FilmItem){
                if (!DataRepository.favourites.contains(filmItem)) {
                    DataRepository.favourites.add(filmItem)
                    filmItem.isFavorite = true
                    recyclerFilm.adapter?.notifyItemChanged(DataRepository.films.indexOf(filmItem))
                } else {
                    DataRepository.favourites.remove(filmItem)
                    filmItem.isFavorite = false
                    recyclerFilm.adapter?.notifyItemChanged(DataRepository.films.indexOf(filmItem))
                }
            }
        })
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