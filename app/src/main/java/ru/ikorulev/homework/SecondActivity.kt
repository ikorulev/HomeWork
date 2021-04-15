package ru.ikorulev.homework

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {
    companion object{
        const val BUTTON_NUMBER = "BUTTON_NUMBER"
    }

    var btnNumber = 0
    var titleStr = ""

    val img by lazy {findViewById<ImageView>(R.id.img)}
    val detail by lazy {findViewById<TextView>(R.id.detail)}
    val btnInvite by lazy {findViewById<Button>(R.id.btnInvite)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        btnNumber = intent.getIntExtra(BUTTON_NUMBER,0)

        when(btnNumber) {
            1 -> {img.setImageDrawable(getDrawable(R.drawable.img1))
                detail.setText(getText(R.string.detail1))
                titleStr = getString(R.string.title1)}
            2 -> {img.setImageDrawable(getDrawable(R.drawable.img2))
                detail.setText(getText(R.string.detail2))
                titleStr = getString(R.string.title2)}
            3 -> {img.setImageDrawable(getDrawable(R.drawable.img3))
                detail.setText(getText(R.string.detail3))
                titleStr = getString(R.string.title3)}
            4 -> {img.setImageDrawable(getDrawable(R.drawable.img4))
                detail.setText(getText(R.string.detail4))
                titleStr = getString(R.string.title4)}
        }

        btnInvite.setOnClickListener(){
            val intent = Intent(Intent.ACTION_SEND)
            intent.setDataAndType(Uri.parse("mailto:"),"text/plain")
            intent.putExtra(Intent.EXTRA_EMAIL,  "tom@example.com")
            intent.putExtra(Intent.EXTRA_TEXT,  "${getString(R.string.messageText)}: $titleStr")

            startActivity(intent)
        }
    }
}