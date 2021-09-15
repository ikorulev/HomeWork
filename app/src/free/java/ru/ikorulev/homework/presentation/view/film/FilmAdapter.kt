package ru.ikorulev.homework.presentation.view.film

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.ikorulev.homework.R
import ru.ikorulev.homework.data.FilmItem
import java.util.*

class FilmAdapter(private val clickListener: FilmClickListener) : RecyclerView.Adapter<FilmVH>() {

    companion object {
        const val TAG = "FilmAdapter"
    }

    private val items = ArrayList<FilmItem>()

    fun setItems(films: List<FilmItem>) {
        items.clear()
        items.addAll(films)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmVH {

        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_film, parent, false)
        return FilmVH(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: FilmVH, position: Int) {

        val item = items[position]
        holder.bind(item)

        holder.filmButton.setOnClickListener {
            clickListener.onFilmClick(item)
        }

        holder.favourites.setOnClickListener {
            clickListener.onFavoriteClick(item)
        }
    }

    interface FilmClickListener {
        fun onFilmClick(filmItem: FilmItem)
        fun onFavoriteClick(filmItem: FilmItem)
    }
}