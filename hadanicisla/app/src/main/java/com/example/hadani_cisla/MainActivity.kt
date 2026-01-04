package com.example.hadani_cisla

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var tvMessage: TextView
    private lateinit var etGuess: EditText
    private lateinit var btnGuess: Button

    private var targetNumber: Int = Random.nextInt(1, 101)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvMessage = findViewById(R.id.tvMessage)
        etGuess = findViewById(R.id.etGuess)
        btnGuess = findViewById(R.id.btnGuess)

        // obnova stavu po otočení obrazovky
        if (savedInstanceState != null) {
            targetNumber = savedInstanceState.getInt("targetNumber", targetNumber)
            tvMessage.text = savedInstanceState.getString("message", getString(R.string.msg_intro))
        } else {
            tvMessage.text = getString(R.string.msg_intro)
        }

        btnGuess.setOnClickListener {
            val guess = etGuess.text.toString().toIntOrNull()
            if (guess == null) {
                tvMessage.text = getString(R.string.msg_nan)
                return@setOnClickListener
            }
            if (guess !in 1..100) {
                tvMessage.text = getString(R.string.msg_out_of_range)
                return@setOnClickListener
            }

            when {
                guess < targetNumber -> tvMessage.text = getString(R.string.msg_bigger)
                guess > targetNumber -> tvMessage.text = getString(R.string.msg_smaller)
                else -> {
                    tvMessage.text = getString(R.string.msg_correct)
                    Toast.makeText(this, getString(R.string.msg_new_game), Toast.LENGTH_SHORT).show()
                    targetNumber = Random.nextInt(1, 101) // nová hra
                    etGuess.text.clear()
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("targetNumber", targetNumber)
        outState.putString("message", tvMessage.text.toString())
    }
}