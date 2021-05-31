package ru.ikorulev.homework

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment

class FilmDetailFragment : Fragment() {
    companion object {
        const val TAG = "FilmDetailFragment"
        const val FILM_NUMBER = "FILM_NUMBER"

        fun newInstance(filmNumber: Int): FilmDetailFragment {
            val args = Bundle()
            args.putInt(FILM_NUMBER, filmNumber)

            val fragment = FilmDetailFragment()
            fragment.arguments = args
            return fragment
        }
    }

    val img by lazy {view?.findViewById<ImageView>(R.id.img)}
    val detail by lazy {view?.findViewById<TextView>(R.id.detail)}
    val btnInvite by lazy {view?.findViewById<Button>(R.id.btnInvite)}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView")
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG, "onViewCreated")
        val filmItem = DataRepository.films[arguments?.getInt(FILM_NUMBER) ?: 0]
        img?.setImageResource(filmItem.filmImage)
        detail?.text = filmItem.filmDetail

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