package ru.ikorulev.homework

import android.graphics.Color
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.ikorulev.homework.MainActivity.Companion.btnNumber

class FilmVH (itemView: View) : RecyclerView.ViewHolder(itemView) {
    val filmImage: ImageView = itemView.findViewById(R.id.filmImage)
    val filmFavorite: ImageView = itemView.findViewById(R.id.filmFavorite)
    val filmTitle: TextView = itemView.findViewById(R.id.filmTitle)
    val filmButton: Button = itemView.findViewById(R.id.filmButton)

    fun bind(item: FilmItem) {
        if (item.filmFavorite) {
            filmFavorite.setImageResource(R.drawable.favorite_fill)
        } else {
            filmFavorite.setImageResource(R.drawable.favorite_border)
        }

        filmTitle.text = item.filmTitle
        if (item.filmNumber == btnNumber) {
            filmTitle.setBackgroundColor(Color.YELLOW)
        } else {
            filmTitle.setBackgroundColor(Color.WHITE)
        }

        filmImage.setImageResource(item.filmImage)
    }
}

