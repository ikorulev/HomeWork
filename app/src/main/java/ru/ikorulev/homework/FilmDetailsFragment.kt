package ru.ikorulev.homework

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

class FilmDetailsFragment : Fragment() {
    companion object {
        const val TAG = "FilmDetailsFragment"
    }

    val img by lazy {view?.findViewById<ImageView>(R.id.img)}
    val details by lazy {view?.findViewById<TextView>(R.id.details)}
    val btnInvite by lazy {view?.findViewById<Button>(R.id.btnInvite)}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var filmItem = DataRepository.films[0]
        DataRepository.films.forEach {
            if (it.isSelected)
                filmItem = it
        }

        img?.setImageResource(filmItem.filmImage)
        details?.text = filmItem.filmDetails

        btnInvite?.setOnClickListener(){
            val intent = Intent(Intent.ACTION_SEND)
            intent.setData(Uri.parse("mailto:"))
            intent.setType("text/plain")
            intent.putExtra(Intent.EXTRA_EMAIL,  "tom@example.com")
            intent.putExtra(Intent.EXTRA_TEXT,  "${getString(R.string.messageText)}: ${filmItem.filmTitle}")

            startActivity(intent)
        }
    }
}