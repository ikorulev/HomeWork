package ru.ikorulev.homework.presentation.view.watch_later

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.ikorulev.homework.R
import ru.ikorulev.homework.data.FilmItem
import java.text.DateFormat.getDateInstance
import java.text.SimpleDateFormat

class WatchLaterVH (itemView: View) : RecyclerView.ViewHolder(itemView) {

    val filmImage: ImageView = itemView.findViewById(R.id.filmImage)
    val watchDateButton: Button = itemView.findViewById(R.id.watchDateButton)
    val filmTitle: TextView = itemView.findViewById(R.id.filmTitle)

    fun bind(item: FilmItem) {

        //val simpleDateFormat = SimpleDateFormat("dd-MM-yy")
        //watchDateButton.text = simpleDateFormat.format(item.watchDate)
        watchDateButton.text = getDateInstance().format(item.watchDate)

        filmTitle.text = item.filmTitle

        Glide.with(filmImage.context)
            .load("https://image.tmdb.org/t/p/w342${item.filmPath}")
            .placeholder(R.drawable.ic_image)
            .error(R.drawable.ic_error)
            .into(filmImage)
    }
}