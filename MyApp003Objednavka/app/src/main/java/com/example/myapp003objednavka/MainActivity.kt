package com.example.myapp003objednavka

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapp003objednavka.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Radio buttons - mění obrázek
        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                binding.radioOption1.id -> binding.productImage.setImageResource(R.drawable.prod_a)
                binding.radioOption2.id -> binding.productImage.setImageResource(R.drawable.prod_a)
                binding.radioOption3.id -> binding.productImage.setImageResource(R.drawable.prod_a)
            }
        }

        // Tlačítko objednat
        binding.btnOrder.setOnClickListener {
            val sb = StringBuilder("Souhrn objednávky:\n")

            // zvolený produkt
            val selectedId = binding.radioGroup.checkedRadioButtonId
            when (selectedId) {
                binding.radioOption1.id -> sb.append("Produkt A\n")
                binding.radioOption2.id -> sb.append("Produkt B\n")
                binding.radioOption3.id -> sb.append("Produkt C\n")
                else -> sb.append("Žádný produkt nevybrán\n")
            }

            // checkboxy
            if (binding.checkOption1.isChecked) sb.append("- Doplňková služba 1\n")
            if (binding.checkOption2.isChecked) sb.append("- Doplňková služba 2\n")
            if (binding.checkOption3.isChecked) sb.append("- Doplňková služba 3\n")

            binding.txtSummary.text = sb.toString()
        }
    }
}
