package com.example.myapp001linearlayout

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // pokud používáš náš LinearLayout XML, ujisti se, že se jmenuje activity_main.xml
        setContentView(R.layout.activity_main)


        val etJmeno = findViewById<EditText>(R.id.editTextJmeno)
        val etPrijmeni = findViewById<EditText>(R.id.editTextPrijmeni)
        val etObec = findViewById<EditText>(R.id.editTextObec)
        val etVek = findViewById<EditText>(R.id.editTextVek)

        val btnOdeslat = findViewById<Button>(R.id.buttonOdeslat)
        val btnVymazat = findViewById<Button>(R.id.buttonVymazat)

        val tvOutput = findViewById<TextView>(R.id.textViewOutput)


        btnOdeslat.setOnClickListener {
            val jmeno = etJmeno.text.toString().trim()
            val prijmeni = etPrijmeni.text.toString().trim()
            val obec = etObec.text.toString().trim()
            val vek = etVek.text.toString().trim()


            val result = buildString {
                appendLine("Jméno: $jmeno")
                appendLine("Příjmení: $prijmeni")
                appendLine("Obec: $obec")
                append("Věk: ${if (vek.isEmpty()) "—" else vek}")
            }
            tvOutput.text = result
        }

        btnVymazat.setOnClickListener {
            etJmeno.text?.clear()
            etPrijmeni.text?.clear()
            etObec.text?.clear()
            etVek.text?.clear()
            tvOutput.text = "text"
        }
    }
}
