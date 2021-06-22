package ru.ikorulev.homework.Favourites

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.ikorulev.homework.DataRepository
import ru.ikorulev.homework.R

class FavouritesFragment : Fragment() {
    companion object {
        const val TAG = "FavouritesFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favourites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val recyclerFavourites = view.findViewById<RecyclerView>(R.id.recyclerFavourites)

        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
            val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            recyclerFavourites.layoutManager = layoutManager

        } else {
            val layoutManager = GridLayoutManager(requireContext(), 2)
            recyclerFavourites.layoutManager = layoutManager
        }

        recyclerFavourites.adapter = FavouritesAdapter(DataRepository.favourites)

    }

}