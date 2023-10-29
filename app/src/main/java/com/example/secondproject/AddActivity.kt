package com.example.secondproject

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

            val db_helper = SQLiteHelper(context = applicationContext)
            db_helper.insert(name, price.toDouble(), quantity.toInt())

            finish()
        }
    }
}