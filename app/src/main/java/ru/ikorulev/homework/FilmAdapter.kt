package ru.ikorulev.homework

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class FilmAdapter (private val items: List<FilmItem>, private val clickListener: FilmClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val TAG = "FilmAdapter"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        //Log.d(TAG, "onCreateViewHolder $viewType")

        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_films, parent, false)
        return FilmVH(view)

    }

    override fun getItemViewType(position: Int) = 0

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //Log.d(TAG, "onBindViewHolder $position")

        if (holder is FilmVH) {
            val item = items[position]
            holder.bind(item)

            holder.filmButton.setOnClickListener {
                clickListener.onFilmClick(item)
            }

            holder.filmFavorite.setOnClickListener {
                clickListener.onFavoriteClick(item)
            }

            /*holder.titleTv.setOnClickListener {
                clickListener.onDeleteClick(item)
            }*/
        }
    }

    interface FilmClickListener {
        fun onFilmClick(filmItem: FilmItem)
        fun onFavoriteClick(filmItem: FilmItem)
        //fun onDeleteClick(filmItem: filmItem)
    }
}