package ru.ikorulev.homework

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class FilmListFragment:Fragment() {
    companion object {
        const val TAG = "FilmListFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance=true

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val recyclerFilm = view.findViewById<RecyclerView>(R.id.recyclerFilm)

        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
            val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            recyclerFilm.layoutManager = layoutManager
        } else {
            val layoutManager = GridLayoutManager(requireContext(), 2)
            recyclerFilm.layoutManager = layoutManager
        }

        recyclerFilm.adapter = FilmAdapter(
            DataRepository.films, object : FilmAdapter.FilmClickListener {
                override fun onFilmClick(filmItem: FilmItem){
                    (activity as? OnFilmDetailsClickListener)?.onFilmDetailsClick(filmItem)
                }

                override fun onFavoriteClick(filmItem: FilmItem){
                    if (!DataRepository.favourites.contains(filmItem)) {

                        DataRepository.favourites.add(filmItem)
                        filmItem.isFavorite = true

                        Snackbar.make(view, "Фильм ${filmItem.filmTitle} успешно добавлен в избранное", Snackbar.LENGTH_SHORT)
                            .setAnimationMode(Snackbar.ANIMATION_MODE_FADE)
                            .show()

                        recyclerFilm?.adapter?.notifyItemChanged(DataRepository.films.indexOf(filmItem))
                    } else {

                        DataRepository.favourites.remove(filmItem)
                        filmItem.isFavorite = false

                        Snackbar.make(view, "Фильм ${filmItem.filmTitle} успешно удален из избранного", Snackbar.LENGTH_SHORT)
                            .setAnimationMode(Snackbar.ANIMATION_MODE_FADE)
                            .show()

                        recyclerFilm?.adapter?.notifyItemChanged(DataRepository.films.indexOf(filmItem))
                    }
                }
            }
        )
    }

    interface OnFilmDetailsClickListener {
        fun onFilmDetailsClick(filmItem: FilmItem)
    }

}