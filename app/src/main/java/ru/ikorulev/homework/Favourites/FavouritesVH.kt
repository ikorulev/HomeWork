package ru.ikorulev.homework.Favourites

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import ru.ikorulev.homework.FilmItem
import ru.ikorulev.homework.R

class FavouritesVH (itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val favouritesImage: ImageView = itemView.findViewById(R.id.favouritesImage)

    fun bind(item: FilmItem) {
        favouritesImage.setImageResource(item.filmImage)
    }
}