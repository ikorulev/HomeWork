package ru.ikorulev.homework.presentation.view.favourites

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.ikorulev.homework.R
import ru.ikorulev.homework.data.FilmItem

class FavouritesVH (itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val favouritesImage: ImageView = itemView.findViewById(R.id.favouritesImage)
    private val favouriteTitle: TextView = itemView.findViewById(R.id.favouriteTitle)
    val favouritesButton: ImageView = itemView.findViewById(R.id.favouritesButton)

    fun bind(item: FilmItem) {
        favouriteTitle.text = item.filmTitle
        Glide.with(favouritesImage.context)
            .load("https://image.tmdb.org/t/p/w342${item.filmPath}")
            .placeholder(R.drawable.ic_image)
            .error(R.drawable.ic_error)
            .into(favouritesImage)

    }
}