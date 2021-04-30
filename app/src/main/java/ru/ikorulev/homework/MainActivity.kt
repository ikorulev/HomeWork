package ru.ikorulev.homework

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    companion object {
        private const val BUTTON_NUMBER = "BUTTON_NUMBER"
        var btnNumber = 0
     }

    private val recyclerView by lazy { findViewById<RecyclerView>(R.id.recyclerView) }
    private val films = mutableListOf<FilmItem>()
    private val favourites = mutableListOf<FilmItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        initFilms()
        initRecycler()

    }

    private fun initFilms() {
        films.add(FilmItem(1, getString(R.string.title1),R.drawable.img1,false))
        films.add(FilmItem(2, getString(R.string.title2),R.drawable.img2,false))
        films.add(FilmItem(3, getString(R.string.title3),R.drawable.img3,false))
        films.add(FilmItem(4, getString(R.string.title4),R.drawable.img4,false))
    }

    private fun initRecycler() {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager

        recyclerView.adapter = FilmAdapter(films, object : FilmAdapter.FilmClickListener {
            override fun onFilmClick(filmItem: FilmItem){
                //filmItem.filmColor = Color.YELLOW
                //recyclerView.adapter?.notifyItemChanged(filmItem.filmNumber-1)

                btnNumber = filmItem.filmNumber
                recyclerView.adapter?.notifyDataSetChanged()

                val intent = Intent(applicationContext, FilmDescription::class.java)

                intent.putExtra(FilmDescription.BUTTON_NUMBER, filmItem.filmNumber)
                startActivity(intent)
            }

            override fun onFavoriteClick(filmItem: FilmItem){
                if (!favourites.contains(filmItem)) {
                    favourites.add(filmItem)
                    filmItem.filmFavorite = true
                    recyclerView.adapter?.notifyItemChanged(filmItem.filmNumber-1)
                }


            //filmItem.filmColor = Color.YELLOW
                //recyclerView.adapter?.notifyItemChanged(filmItem.filmNumber-1)

                //btnNumber = filmItem.filmNumber
                //recyclerView.adapter?.notifyDataSetChanged()

                //val intent = Intent(applicationContext, FilmDescription::class.java)

                //intent.putExtra(FilmDescription.BUTTON_NUMBER, filmItem.filmNumber)
                //startActivity(intent)
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
}