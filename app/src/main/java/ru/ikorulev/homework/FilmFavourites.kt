package ru.ikorulev.homework

import android.content.res.Configuration
import android.media.VolumeShaper
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FilmFavourites : AppCompatActivity() {
    companion object{
        const val LIST_FAVOURITES = "LIST_FAVOURITES"
    }
    private val recyclerFavourites by lazy { findViewById<RecyclerView>(R.id.recyclerFavourites) }
    private var favourites = arrayListOf<FilmItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourites)
        favourites = intent.getParcelableArrayListExtra<FilmItem>(LIST_FAVOURITES) as ArrayList<FilmItem>
        initRecycler()
    }

    private fun initRecycler() {
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
            val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            recyclerFavourites.layoutManager = layoutManager

        } else {
            val layoutManager = GridLayoutManager(this, 2)
            recyclerFavourites.layoutManager = layoutManager
        }
        recyclerFavourites.adapter = FavoriteAdapter(favourites)
     }

    class FavoriteAdapter(private val items: List <FilmItem>) : RecyclerView.Adapter<FavoriteVH>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteVH {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.item_favorites, parent, false)

            return FavoriteVH(view)

        }

        override fun onBindViewHolder(holder: FavoriteVH, position: Int) {
            val item = items[position]

            holder.bind(item)
        }

        override fun getItemCount(): Int = items.size
    }


    class FavoriteVH (itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val favoriteImage: ImageView = itemView.findViewById(R.id.favoriteImage)

        fun bind(item: FilmItem) {
            favoriteImage.setImageResource(item.filmImage)
        }
    }

}