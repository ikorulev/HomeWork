package ru.ikorulev.homework.presentation.view

import android.content.DialogInterface
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import ru.ikorulev.homework.R
import ru.ikorulev.homework.data.FilmItem
import ru.ikorulev.homework.presentation.view.film.FilmAdapter
import ru.ikorulev.homework.presentation.viewmodel.FilmViewModel


class FilmListFragment : Fragment() {
    companion object {
        const val TAG = "FilmListFragment"
    }

    private val viewModel: FilmViewModel by activityViewModels()
    private var filmPage = 1

    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val recyclerFilm = view.findViewById<RecyclerView>(R.id.recyclerFilm)

        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            recyclerFilm.layoutManager = layoutManager
        } else {
            layoutManager = GridLayoutManager(requireContext(), 2)
            recyclerFilm.layoutManager = layoutManager
        }

        val adapter = FilmAdapter(object : FilmAdapter.FilmClickListener {
            override fun onFilmClick(filmItem: FilmItem) {
                viewModel.onFilmClick(filmItem)
                (activity as? OnFilmDetailsClickListener)?.onFilmDetailsClick(filmItem)
            }

            override fun onFavoriteClick(filmItem: FilmItem) {
                if (viewModel.onFavoriteClick(filmItem) == true) {
                    Snackbar.make(
                        view,
                        "Фильм ${filmItem.filmTitle} успешно добавлен в избранное",
                        Snackbar.LENGTH_SHORT
                    )
                        .setAnimationMode(Snackbar.ANIMATION_MODE_FADE)
                        .show()
                } else {
                    Snackbar.make(
                        view,
                        "Фильм ${filmItem.filmTitle} успешно удален из избранного",
                        Snackbar.LENGTH_SHORT
                    )
                        .setAnimationMode(Snackbar.ANIMATION_MODE_FADE)
                        .show()
                }
                viewModel.films?.value?.let {
                    recyclerFilm?.adapter?.notifyItemChanged(
                        viewModel.indexOfFilm(filmItem)
                    )
                }
            }
        })
        recyclerFilm.adapter = adapter
        recyclerFilm.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (layoutManager.findLastVisibleItemPosition() >= layoutManager.itemCount * 0.7) {
                    filmPage++
                    viewModel.loadFilms(filmPage)
                }
            }
        })


        viewModel.films?.observe(viewLifecycleOwner, { filmsDb ->
            val films = mutableListOf<FilmItem>()
            filmsDb.forEach {
                films.add(
                    FilmItem(
                        it.filmTitle,
                        it.filmPath,
                        it.filmDetails,
                        it.isSelected,
                        it.isFavorite
                    )
                )
            }
            adapter.setItems(films)
        })

        viewModel.errors.observe(viewLifecycleOwner, { error ->
            if (error.length > 0) {
                AlertDialog.Builder(requireContext()).apply {
                    setTitle(error)
                    setMessage(getString(R.string.Retry))

                    setPositiveButton("Да") { dialogInterface: DialogInterface, i: Int ->
                        viewModel.loadFilms()
                    }
                    setNegativeButton("Нет") { dialogInterface: DialogInterface, i: Int -> }

                    setCancelable(true)
                }.create().show()
                viewModel.clearError()
            }
        })

    }

    interface OnFilmDetailsClickListener {
        fun onFilmDetailsClick(filmItem: FilmItem)
    }

}