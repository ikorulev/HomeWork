package ru.ikorulev.homework

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    companion object {
        private const val BUTTON_NUMBER = "BUTTON_NUMBER"
     }

    var btnNumber = 0

    val btn1 by lazy { findViewById<Button>(R.id.btn1) }
    val btn2 by lazy { findViewById<Button>(R.id.btn2) }
    val btn3 by lazy { findViewById<Button>(R.id.btn3) }
    val btn4 by lazy { findViewById<Button>(R.id.btn4) }

    val title1 by lazy { findViewById<TextView>(R.id.title1) }
    val title2 by lazy { findViewById<TextView>(R.id.title2) }
    val title3 by lazy { findViewById<TextView>(R.id.title3) }
    val title4 by lazy { findViewById<TextView>(R.id.title4) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn1.setOnClickListener() {
            btnNumber = 1
            title1.background = getDrawable(R.color.yellow)
            title2.background = getDrawable(R.color.white)
            title3.background = getDrawable(R.color.white)
            title4.background = getDrawable(R.color.white)

            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra(SecondActivity.BUTTON_NUMBER, btnNumber)
            startActivity(intent)
        }

        btn2.setOnClickListener() {
            btnNumber = 2
            title1.background = getDrawable(R.color.white)
            title2.background = getDrawable(R.color.yellow)
            title3.background = getDrawable(R.color.white)
            title4.background = getDrawable(R.color.white)

            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra(SecondActivity.BUTTON_NUMBER, btnNumber)
            startActivity(intent)
        }

        btn3.setOnClickListener() {
            btnNumber = 3
            title1.background = getDrawable(R.color.white)
            title2.background = getDrawable(R.color.white)
            title3.background = getDrawable(R.color.yellow)
            title4.background = getDrawable(R.color.white)

            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra(SecondActivity.BUTTON_NUMBER, btnNumber)
            startActivity(intent)
        }

        btn4.setOnClickListener() {
            btnNumber = 4
            title1.background = getDrawable(R.color.white)
            title2.background = getDrawable(R.color.white)
            title3.background = getDrawable(R.color.white)
            title4.background = getDrawable(R.color.yellow)

            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra(SecondActivity.BUTTON_NUMBER, btnNumber)
            startActivity(intent)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(BUTTON_NUMBER, btnNumber)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        savedInstanceState?.let {
            btnNumber = it.getInt(BUTTON_NUMBER)
            if (btnNumber == 1) {
                title1.background = getDrawable(R.color.yellow)
                title2.background = getDrawable(R.color.white)
                title3.background = getDrawable(R.color.white)
                title4.background = getDrawable(R.color.white)
            } else if (btnNumber == 2) {
                title1.background = getDrawable(R.color.white)
                title2.background = getDrawable(R.color.yellow)
                title3.background = getDrawable(R.color.white)
                title4.background = getDrawable(R.color.white)
            } else if (btnNumber == 3) {
                title1.background = getDrawable(R.color.white)
                title2.background = getDrawable(R.color.white)
                title3.background = getDrawable(R.color.yellow)
                title4.background = getDrawable(R.color.white)
            } else if (btnNumber == 4) {
                title1.background = getDrawable(R.color.white)
                title2.background = getDrawable(R.color.white)
                title3.background = getDrawable(R.color.white)
                title4.background = getDrawable(R.color.yellow)
            } else {
                title1.background = getDrawable(R.color.white)
                title2.background = getDrawable(R.color.white)
                title3.background = getDrawable(R.color.white)
                title4.background = getDrawable(R.color.white)
            }
        }
    }
}