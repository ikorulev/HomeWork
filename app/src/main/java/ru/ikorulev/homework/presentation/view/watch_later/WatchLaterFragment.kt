package ru.ikorulev.homework.presentation.view.watch_later

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
import ru.ikorulev.homework.R
import ru.ikorulev.homework.data.FilmItem
import ru.ikorulev.homework.presentation.view.DatePickerFragment
import ru.ikorulev.homework.presentation.viewmodel.FilmViewModel

class WatchLaterFragment : Fragment() {
    companion object {
        const val TAG = "WatchLaterFragment"
    }

    private val viewModel: FilmViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_watch_later, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val recyclerWatchLater = view.findViewById<RecyclerView>(R.id.recyclerWatchLater)

        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            val layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            recyclerWatchLater.layoutManager = layoutManager

        } else {
            val layoutManager = GridLayoutManager(requireContext(), 2)
            recyclerWatchLater.layoutManager = layoutManager
        }

        val adapter = WatchLaterAdapter(object : WatchLaterAdapter.WatchLaterListener {

            override fun onWatchDateButtonClick(filmItem: FilmItem) {
                viewModel.datePickerFilmItem = filmItem
                val datePickerFragment = DatePickerFragment()
                datePickerFragment.show(childFragmentManager, "DatePickerFragment")
            }
        })

        recyclerWatchLater.adapter = adapter

        viewModel.watchLater.observe(viewLifecycleOwner, { watchLater ->
            adapter.setItems(watchLater)
        })
    }
}