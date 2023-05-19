package com.example.scancarev001

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.scancarev001.databinding.ActivityDetailBinding
import com.example.scancarev001.databinding.ActivityMainBinding

class DetailActivity : AppCompatActivity() {
    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val penyakit = intent.getStringExtra("Data")

        binding.tv1.text = "Muka anda banyak ${penyakit.toString()}, anda kebanyakan main hp"
    }
}