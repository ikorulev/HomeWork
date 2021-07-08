package ru.ikorulev.homework.presentation.view.film

import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.ikorulev.homework.App
//import kotlinx.android.extensions.LayoutContainer
//import kotlinx.android.synthetic.main.item_main.*

import ru.ikorulev.homework.R
import ru.ikorulev.homework.data.FilmItem
import ru.ikorulev.homework.domain.Interactor


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

        Glide.with(filmImage.context)
            .load("https://image.tmdb.org/t/p/w342${item.filmPath}")
            .placeholder(R.drawable.ic_image)
            .error(R.drawable.ic_error)
            .into(filmImage)

    }
}

