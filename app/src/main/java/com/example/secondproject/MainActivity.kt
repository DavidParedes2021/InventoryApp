package com.example.secondproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.secondproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.Addbutton.setOnClickListener {
            startActivity(Intent(this, AddActivity::class.java))
        }

        binding.EditButton.setOnClickListener {
            startActivity(Intent(this, EditActivity::class.java))
        }
    }
}