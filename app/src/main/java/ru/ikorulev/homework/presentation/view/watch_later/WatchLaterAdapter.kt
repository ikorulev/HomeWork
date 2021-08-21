package ru.ikorulev.homework.presentation.view.watch_later

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.ikorulev.homework.R
import ru.ikorulev.homework.data.FilmItem
import java.util.ArrayList

class WatchLaterAdapter (private val clickListener: WatchLaterListener) : RecyclerView.Adapter<WatchLaterVH>() {

    private val items = ArrayList<FilmItem>()

    fun setItems(films: List<FilmItem>) {
        items.clear()
        items.addAll(films)

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WatchLaterVH {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_watch_later, parent, false)

        return WatchLaterVH(view)
    }

    override fun onBindViewHolder(holder: WatchLaterVH, position: Int) {
        val item = items[position]

        holder.bind(item)

        holder.watchDateButton.setOnClickListener {
            clickListener.onWatchDateButtonClick(item)
        }

        holder.watchLater.setOnClickListener {
            clickListener.onWatchLaterClick(item)
        }
    }

    override fun getItemCount(): Int = items.size

    interface WatchLaterListener {
        fun onWatchDateButtonClick(filmItem: FilmItem)
        fun onWatchLaterClick(filmItem: FilmItem)
    }
}