package com.example.secondproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.secondproject.databinding.ActivityAddBinding

class AddActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.SaveButton.setOnClickListener {
            val name = binding.NameInput.editText?.text?.toString() ?: ""
            val price = binding.PriceInput.editText?.text?.toString() ?: ""
            val quantity = binding.QuantityInput.editText?.text?.toString() ?: ""

            if (name.isBlank() || price.isBlank() || quantity.isBlank()) {
                Toast.makeText(this, "Llena todos los campos", Toast.LENGTH_LONG).show()
            } else {
                SQLiteHelper(context = applicationContext).apply {
                    insert(name, price.toDouble(), quantity.toInt())
                }

                finish()
            }
        }
    }
}