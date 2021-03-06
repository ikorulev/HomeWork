package ru.ikorulev.homework.presentation.view.favourites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.ikorulev.homework.R
import ru.ikorulev.homework.data.FilmItem
import java.util.*

class FavouritesAdapter (private val clickListener: FavouritesClickListener)  : RecyclerView.Adapter<FavouritesVH>() {

    private val items = ArrayList<FilmItem>()

    fun setItems(films: List<FilmItem>) {
        items.clear()
        items.addAll(films)

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouritesVH {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_favourites, parent, false)

        return FavouritesVH(view)
    }

    override fun onBindViewHolder(holder: FavouritesVH, position: Int) {
        val item = items[position]

        holder.bind(item)
        holder.favouritesButton.setOnClickListener {
            clickListener.onFavoriteClick(item)
        }
    }

    override fun getItemCount(): Int = items.size

    interface FavouritesClickListener {
        fun onFavoriteClick(filmItem: FilmItem)
    }
}