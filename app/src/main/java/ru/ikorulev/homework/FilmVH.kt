package ru.ikorulev.homework

import android.graphics.Color
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class FilmVH (itemView: View) : RecyclerView.ViewHolder(itemView) {

    val filmImage: ImageView = itemView.findViewById(R.id.filmImage)
    val filmFavorite: ImageView = itemView.findViewById(R.id.filmFavourites)
    val filmTitle: TextView = itemView.findViewById(R.id.filmTitle)
    val filmButton: Button = itemView.findViewById(R.id.filmButton)

    fun bind(item: FilmItem) {
        if (item.isFavorite) {
            filmFavorite.setImageResource(R.drawable.favourites_fill)
        } else {
            filmFavorite.setImageResource(R.drawable.favourites_border)
        }

        filmTitle.text = item.filmTitle
        if (item.isSelected) {
            filmTitle.setBackgroundColor(Color.YELLOW)
        } else {
            filmTitle.setBackgroundColor(Color.WHITE)
        }

        filmImage.setImageResource(item.filmImage)
    }
}

