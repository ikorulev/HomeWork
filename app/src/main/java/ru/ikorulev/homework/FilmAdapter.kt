package ru.ikorulev.homework

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class FilmAdapter (private val items: List<FilmItem>, private val clickListener: FilmClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {

/*
        const val TAG = "NewsAdapter"
        const val VIEW_TYPE_NEWS = 0
        const val VIEW_TYPE_HEADER = 1
*/
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        //Log.d(TAG, "onCreateViewHolder $viewType")
        // xml -> View = inflate
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_films, parent, false)
        return FilmVH(view)

 /*       return if (viewType == VIEW_TYPE_NEWS) {
            val view = layoutInflater.inflate(R.layout.item_films, parent, false)
            FilmVH(view)
        } else {
            val view = layoutInflater.inflate(R.layout.item_header, parent, false)
            NewsHeaderVH(view)
        }*/
    }

    override fun getItemViewType(position: Int) = 0
            //= if (position == 0) VIEW_TYPE_HEADER else VIEW_TYPE_NEWS


    override fun getItemCount() = items.size // +1 = header

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //Log.d(TAG, "onBindViewHolder $position")

        if (holder is FilmVH) {
            val item = items[position]
            holder.bind(item)

            holder.filmButton.setOnClickListener {
                clickListener.onFilmClick(item)
            }

            /*holder.image.setOnClickListener {
                clickListener.onFavoriteClick(item)
            }

            holder.titleTv.setOnClickListener {
                clickListener.onDeleteClick(item)
            }*/
        }
    }

    interface FilmClickListener {
        fun onFilmClick(filmItem: FilmItem)
        //fun onFavoriteClick(filmItem: filmItem)
        //fun onDeleteClick(filmItem: filmItem)
    }
}