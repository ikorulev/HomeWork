package ru.ikorulev.homework.Favourites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.ikorulev.homework.FilmItem
import ru.ikorulev.homework.R

class FavouritesAdapter (private val items: List <FilmItem>) : RecyclerView.Adapter<FavouritesVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouritesVH {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_favourites, parent, false)

        return FavouritesVH(view)
    }

    override fun onBindViewHolder(holder: FavouritesVH, position: Int) {
        val item = items[position]

        holder.bind(item)
    }

    override fun getItemCount(): Int = items.size

}