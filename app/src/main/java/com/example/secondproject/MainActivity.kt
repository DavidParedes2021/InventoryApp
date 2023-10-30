package com.example.secondproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.secondproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sqLiteHelper: SQLiteHelper
    private lateinit var productAdapter: ProductAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sqLiteHelper = SQLiteHelper(context = applicationContext)

        val productsList = sqLiteHelper.getProducts()
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        // Initialize the adapter and set it to the RecyclerView
        productAdapter = ProductAdapter(productsList)
        binding.recyclerView.adapter = productAdapter

        binding.addProductBtn.setOnClickListener {
            startActivity(Intent(this, AddActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()

        // Refresh the list of products when returning from AddActivity
        val updatedProductsList = sqLiteHelper.getProducts()

        // Update the adapter with the new list and notify changes
        productAdapter.updateProductList(updatedProductsList)
    }
}