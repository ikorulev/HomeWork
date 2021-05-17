package ru.ikorulev.homework

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class FilmDescription : AppCompatActivity() {
    companion object{
        const val FILM_NUMBER = "FILM_NUMBER"
    }

    val img by lazy {findViewById<ImageView>(R.id.img)}
    val detail by lazy {findViewById<TextView>(R.id.detail)}
    val btnInvite by lazy {findViewById<Button>(R.id.btnInvite)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)

        val filmItem = DataRepository.films[intent.getIntExtra(FILM_NUMBER,0)]

        img?.setImageResource(filmItem.filmImage)
        detail?.text = filmItem.filmDetail

        btnInvite.setOnClickListener(){
            val intent = Intent(Intent.ACTION_SEND)
            intent.setData(Uri.parse("mailto:"))
            intent.setType("text/plain")
            intent.putExtra(Intent.EXTRA_EMAIL,  "tom@example.com")
            intent.putExtra(Intent.EXTRA_TEXT,  "${getString(R.string.messageText)}: ${filmItem.filmTitle}")

            startActivity(intent)
        }
    }
}