package ru.ikorulev.homework.presentation.view.favourites

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.ikorulev.homework.data.FilmItem
import ru.ikorulev.homework.R

class FavouritesVH (itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val filmImage: ImageView = itemView.findViewById(R.id.filmImage)
    private val filmTitle: TextView = itemView.findViewById(R.id.filmTitle)
    val filmFavorite: ImageView = itemView.findViewById(R.id.filmFavourites)

    fun bind(item: FilmItem) {
        filmTitle.text = item.filmTitle
        Glide.with(filmImage.context)
            .load("https://image.tmdb.org/t/p/w342${item.filmPath}")
            .placeholder(R.drawable.ic_image)
            .error(R.drawable.ic_error)
            .into(filmImage)

    }
}