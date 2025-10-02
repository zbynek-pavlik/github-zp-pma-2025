package com.example.myapp002myownconstaraintlayout

import android.content.Context
import android.os.Bundle
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    private lateinit var etGoal: EditText
    private lateinit var btnSetGoal: MaterialButton
    private lateinit var tvCount: TextView
    private lateinit var tvPercent: TextView
    private lateinit var progress: ProgressBar
    private lateinit var btnMinus: MaterialButton
    private lateinit var btnPlus: MaterialButton
    private lateinit var btnReset: MaterialButton
    private lateinit var tvTip: TextView

    private var goal: Int = 8            // výchozí denní cíl (sklenic)
    private var count: Int = 0           // aktuálně vypito (sklenic)

    private val prefs by lazy {
        getSharedPreferences("hydro_prefs", Context.MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindViews()
        loadState()
        render()

        btnSetGoal.setOnClickListener {
            val entered = etGoal.text.toString().toIntOrNull()
            if (entered != null && entered > 0) {
                goal = entered
                if (count > goal) count = goal
                saveState()
                render()
            } else {
                etGoal.error = "Zadej kladné číslo"
            }
        }

        btnPlus.setOnClickListener {
            if (count < goal) {
                count++
                saveState()
                render()
            }
        }

        btnMinus.setOnClickListener {
            if (count > 0) {
                count--
                saveState()
                render()
            }
        }

        btnReset.setOnClickListener {
            count = 0
            saveState()
            render()
        }
    }

    private fun bindViews() {
        etGoal = findViewById(R.id.etGoal)
        btnSetGoal = findViewById(R.id.btnSetGoal)
        tvCount = findViewById(R.id.tvCount)
        tvPercent = findViewById(R.id.tvPercent)
        progress = findViewById(R.id.progress)
        btnMinus = findViewById(R.id.btnMinus)
        btnPlus = findViewById(R.id.btnPlus)
        btnReset = findViewById(R.id.btnReset)
        tvTip = findViewById(R.id.tvTip)
    }

    private fun loadState() {
        goal = prefs.getInt("goal", 8)
        count = prefs.getInt("count", 0)
        etGoal.setText(goal.toString())
    }

    private fun saveState() {
        prefs.edit()
            .putInt("goal", goal)
            .putInt("count", count)
            .apply()
    }

    private fun render() {
        tvCount.text = "$count sklenic"
        val percent = if (goal > 0) ((count.toFloat() / goal) * 100f) else 0f
        val percentInt = percent.roundToInt().coerceIn(0, 100)
        tvPercent.text = "$percentInt %"
        progress.progress = percentInt

        tvTip.text = when {
            percentInt >= 100 -> "Skvělá práce! Cíl splněn 💧"
            percentInt >= 75  -> "Už jen kousek!"
            percentInt >= 50  -> "Polovina za tebou!"
            percentInt >= 25  -> "Dobrý začátek, pokračuj."
            else              -> "Tip: dej si sklenici vždy před jídlem."
        }
    }
}
