package ru.ikorulev.homework

import android.graphics.Color
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ru.ikorulev.homework.MainActivity.Companion.btnNumber

class FilmVH (itemView: View) : RecyclerView.ViewHolder(itemView) {
    val filmImage: ImageView = itemView.findViewById(R.id.filmImage)
    val filmTitle: TextView = itemView.findViewById(R.id.filmTitle)
    val filmButton: Button = itemView.findViewById(R.id.filmButton)

    fun bind(item: FilmItem) {
        filmTitle.text = item.filmTitle
        //filmTitle.setBackgroundColor(item.filmColor)
        filmImage.setImageResource(item.filmImage)
        if (item.filmNumber == btnNumber) {
            filmTitle.setBackgroundColor(Color.YELLOW)
        } else {
            filmTitle.setBackgroundColor(Color.WHITE)
        }
    }
}

