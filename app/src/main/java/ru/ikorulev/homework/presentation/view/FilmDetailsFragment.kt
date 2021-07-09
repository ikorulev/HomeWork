package ru.ikorulev.homework.presentation.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import ru.ikorulev.homework.R
import ru.ikorulev.homework.data.FilmItem
import ru.ikorulev.homework.presentation.viewmodel.FilmViewModel

class FilmDetailsFragment : Fragment() {
    companion object {
        const val TAG = "FilmDetailsFragment"
    }


    val details by lazy { view?.findViewById<TextView>(R.id.details) }

    private var film: FilmItem? = null
    private val btnInvite by lazy { view?.findViewById<Button>(R.id.btnInvite) }

    private val viewModel: FilmViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val filmImage: ImageView = requireView().findViewById(R.id.filmImage)

        viewModel.selectedFilms.observe(viewLifecycleOwner, { filmItem ->

            Glide.with(filmImage.context)
                .load("https://image.tmdb.org/t/p/w342${filmItem?.filmPath}")
                .placeholder(R.drawable.ic_image)
                .error(R.drawable.ic_error)
                .into(filmImage)

            details?.text = filmItem?.filmDetails

            film = filmItem
        })

        btnInvite?.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.data = Uri.parse("mailto:")
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_EMAIL, "tom@example.com")
            intent.putExtra(
                Intent.EXTRA_TEXT,
                "${getString(R.string.messageText)}: ${film?.filmTitle}"
            )

            startActivity(intent)
        }
    }
}