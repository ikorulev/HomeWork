package ru.ikorulev.homework.presentation.view.favourites

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import ru.ikorulev.homework.R
import ru.ikorulev.homework.data.FilmItem
import ru.ikorulev.homework.presentation.viewmodel.FilmViewModel

class FavouritesFragment : Fragment() {
    companion object {
        const val TAG = "FavouritesFragment"
    }

    private val viewModel: FilmViewModel by activityViewModels()
    private val adapter = FavouritesAdapter(object : FavouritesAdapter.FavouritesClickListener {

        override fun onFavoriteClick(filmItem: FilmItem) {
            viewModel.deleteFavourites(filmItem)
            view?.let {
                Snackbar.make(
                    it,
                    "Фильм ${filmItem.filmTitle} успешно удален из избранного",
                    Snackbar.LENGTH_SHORT
                )
                    .setAnimationMode(Snackbar.ANIMATION_MODE_FADE)
                    .show()
            }
        }
    })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favourites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val recyclerFavourites = view.findViewById<RecyclerView>(R.id.recyclerFavourites)

        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            val layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            recyclerFavourites.layoutManager = layoutManager

        } else {
            val layoutManager = GridLayoutManager(requireContext(), 2)
            recyclerFavourites.layoutManager = layoutManager
        }

        recyclerFavourites.adapter = adapter

        viewModel.favourites.observe(viewLifecycleOwner, { favourites ->
            adapter.setItems(favourites)
        })
    }

}