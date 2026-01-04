package com.example.myapp007bfragmentsexample1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonA: Button = findViewById(R.id.buttonFragmentA)
        val buttonB: Button = findViewById(R.id.buttonFragmentB)

        // Výchozí fragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, FragmentA())
            .commit()

        buttonA.setOnClickListener {
            replaceFragment(FragmentA())
        }

        buttonB.setOnClickListener {
            replaceFragment(FragmentB())
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }
}
