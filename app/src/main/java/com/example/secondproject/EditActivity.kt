package com.example.secondproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.secondproject.databinding.ActivityEditBinding

class EditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditBinding
    private var productId: Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        productId = intent.getIntExtra("productId", -1)
        val productName = intent.getStringExtra("productName") ?: ""
        val productPrice = intent.getDoubleExtra("productPrice", 0.0)
        val productQuantity = intent.getIntExtra("productQuantity", 0)

        binding.NameInput.editText?.setText(productName)
        binding.PriceInput.editText?.setText(productPrice.toString())
        binding.QuantityInput.editText?.setText(productQuantity.toString())

        binding.EditButton.setOnClickListener {
            val name = binding.NameInput.editText?.text?.toString() ?: ""
            val price = binding.PriceInput.editText?.text?.toString() ?: ""
            val quantity = binding.QuantityInput.editText?.text?.toString() ?: ""

            if (name.isBlank() || price.isBlank() || quantity.isBlank()){
                Toast.makeText(this, "Llena todos los campos", Toast.LENGTH_LONG).show()
            } else {
                SQLiteHelper(context = applicationContext).apply {
                    update(productId, name, price.toDouble(), quantity.toInt())
                }
                finish()
            }
        }
    }
}